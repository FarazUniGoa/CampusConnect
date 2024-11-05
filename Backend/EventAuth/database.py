from sqlalchemy import create_engine, MetaData
from databases import Database

DATABASE_URL = "mysql+pymysql://root:root%40123@localhost/event_booking"


engine = create_engine(DATABASE_URL)
metadata = MetaData()

database = Database(DATABASE_URL)
