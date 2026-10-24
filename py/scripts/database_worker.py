"""
Instantiates and returns a MySQL connection
"""
from sqlalchemy.engine import create_engine, Engine
from dotenv import load_dotenv
import os

load_dotenv()
class DatabaseWorker():
    def __init__(self):
        self.host = os.getenv('DATABASE_HOST')
        self.port = os.getenv('PORT')
        self.user =  os.getenv('DATABASE_USER')
        self.passwd = os.getenv('DATABASE_PASSWD')
        self.db_name = os.getenv('DATABASE_NAME')

    def _get_connection(self) -> Engine:
        return create_engine(f"mysql+pymysql://{self.user}:{self.passwd}@{self.host}:{self.port}/{self.db_name}")