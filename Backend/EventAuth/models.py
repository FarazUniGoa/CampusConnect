from sqlalchemy import Table, Column, Integer, String, BigInteger, Text, DateTime, DECIMAL
from database import metadata

events = Table(
    "events",
    metadata,
    Column("id", BigInteger, primary_key=True, autoincrement=True),
    Column("title", String(255), nullable=False),
    Column("description", Text, nullable=True),
    Column("image_url", String(255), nullable=True),
    Column("capacity", Integer, nullable=False),
    Column("price", DECIMAL(10, 2), nullable=True),
    Column("date", DateTime, nullable=True),
    Column("created_by", BigInteger, nullable=True),
)
