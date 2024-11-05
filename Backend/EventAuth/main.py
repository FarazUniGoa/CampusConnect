import requests
from fastapi import FastAPI, HTTPException, Depends
from fastapi.security import HTTPBearer, HTTPAuthorizationCredentials
from pydantic import BaseModel
from firebase_admin import auth
import firebase_admin
import firebase_config  # Ensure you're importing firebase_config to initialize the app
from database import database, engine, metadata
from models import events
from sqlalchemy.sql import select

app = FastAPI()

class User(BaseModel):
    email: str
    password: str

class Event(BaseModel):
    id: int
    title: str
    description: str = None
    image_url: str = None
    capacity: int
    price: float = None
    date: str = None
    created_by: int = None

class AuthResponse(BaseModel):
    success: bool
    uid: str = None
    id_token: str = None
    message: str = None

@app.on_event("startup")
async def startup():
    await database.connect()
    metadata.create_all(engine)

@app.on_event("shutdown")
async def shutdown():
    await database.disconnect()

def exchange_custom_token_for_id_token(custom_token):
    url = f"https://identitytoolkit.googleapis.com/v1/accounts:signInWithCustomToken?key=FireBaseAPIKey"
    payload = {
        "token": custom_token,
        "returnSecureToken": True
    }
    response = requests.post(url, json=payload)
    response_data = response.json()
    id_token = response_data.get('idToken')
    if not id_token:
        print(f"Error exchanging token: {response_data}")
        return None
    print(f"Exchanged Custom Token for ID Token: {id_token}")
    return id_token

@app.post("/login", response_model=AuthResponse)
async def login_user(user: User):
    try:
        firebase_user = auth.get_user_by_email(user.email)
        custom_token = auth.create_custom_token(firebase_user.uid).decode('utf-8')
        id_token = exchange_custom_token_for_id_token(custom_token)
        if not id_token:
            raise HTTPException(status_code=500, detail="Unable to exchange custom token for ID token")
        return {"success": True, "uid": firebase_user.uid, "id_token": id_token}
    except firebase_admin._auth_utils.UserNotFoundError:
        print("User not found")
        return {"success": False, "message": "No user found or wrong credentials"}

@app.get("/events/", response_model=list[Event])
async def read_events(credentials: HTTPAuthorizationCredentials = Depends(HTTPBearer())):
    try:
        id_token = credentials.credentials
        print(f"Received ID token: {id_token}")
        
        # Adding detailed logging for verification
        try:
            decoded_token = auth.verify_id_token(id_token)
            print(f"Decoded token: {decoded_token}")
        except Exception as verify_exception:
            print(f"Token verification failed: {verify_exception}")
            raise HTTPException(status_code=403, detail="Token verification failed")
        
        uid = decoded_token['uid']
        print(f"Extracted UID from token: {uid}")
        
        # Verify user permissions if necessary
        # Here you can add additional checks based on your application's needs
        
        query = select(events)
        results = await database.fetch_all(query)
        print(f"Fetched events: {results}")
        return [Event(**dict(result)) for result in results]
    except Exception as e:
        print(f"Exception during token verification: {e}")
        raise HTTPException(status_code=403, detail="Forbidden")

@app.post("/create-user", response_model=AuthResponse)
async def create_user(user: User):
    try:
        user_record = auth.create_user(
            email=user.email,
            password=user.password
        )
        return {"success": True, "uid": user_record.uid}
    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))

if __name__ == '__main__':
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
