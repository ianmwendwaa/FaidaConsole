from scripts import database_worker as db
import mysql.connector as cnt
from mysql.connector.cursor import MySQLCursor
from datetime import datetime
from sqlalchemy import MetaData, Table, Column, String, Integer, DECIMAL, DateTime, Time, select, insert
import json
import numpy as np
import os
import sys
from dotenv import load_dotenv
import matplotlib.pyplot as plt

load_dotenv()
class DailyRecordsController:
    def __init__(self):
        metadata = MetaData()
        self.daily_records_table = Table(
            "daily_records", metadata,
            Column("record_date", DateTime),
            Column("time_clocked_in", Time),
            Column("time_clocked_out", Time),
            Column("male_customers", Integer),
            Column("female_customers", Integer),
            Column("total_revenue", DECIMAL),
            Column("merchant_comments", String)
        )
        self.db_config = {
            "host": os.getenv('DATABASE_HOST'),
            "username": os.getenv('DATABASE_USER'),
            "passwd": os.getenv('DATABASE_PASSWD'),
            "database": os.getenv('DATABASE_NAME')
        }
    def _get_local_connection(self):
        return cnt.connect(**self.db_config)

    def update_item_overview(self, cursor: MySQLCursor, deserialized_item_data):
        """
            UPDATE ITEM OVERVIEW 
        
            Updates the item data in hindsight. That is, the remaining quantity, and the net gain the product
            brought to our business. Positive gain means profit, negative means loss.
        
            However, the net gain is to be calculated only if the remaining qty is approaching 0 (3 < x < 10)
        
        """
        # Calculate how much stock is left for this item. Get the total quantity where sig = item_sig
        total_item_qty, net_gain = 0
        get_qty_query = "SELECT product_quantity FROM product_data WHERE generated_item_signature = %s"

        # Use the item signature from the parent table to get the original qty        
        cursor.execute(get_qty_query, (deserialized_item_data['itemSignature'], ))
        total_item_qty = cursor.fetchone()[0]
        remaining_qty = total_item_qty - deserialized_item_data['qtySold']

        update_item_overview = "INSERT INTO item_overview (items_signature, original_qty, remaining_qty, net_gain)" \
        "VALUES(%s, %s, %s, %s)" \
        "ON DUPLICATE KEY UPDATE remaining_qty = remaining_qty - %s"

        if remaining_qty < 50:
            print(f"Stock is running low! Remaining: {remaining_qty} for item {deserialized_item_data['itemSignature']}")
        cursor.execute(update_item_overview, (deserialized_item_data['itemSignature'], total_item_qty, remaining_qty, 0, deserialized_item_data['qtySold']))

    
    def create_daily_record(self, args):
        # conn = database_worker.DatabaseWorker()._get_connection()        
        if len(args) < 7:
            print(f"Expected 7 arguments but got {len(args)}") 

        str_date, time_in, time_out, male_customers, female_customers, comments, items_json_payload = args

        # Convert the date to a DATE; MySQL's complaining (Mismatching data types)
        recordDate = datetime.strptime(str_date, "%d/%m/%Y")
        # Parse the json payload to a list
        sales_items_list = json.loads(items_json_payload)
        print(sales_items_list)

        # Calculate the day's total revenue
        total_revenue = 0

        for item in sales_items_list:
            total_revenue += item['qtySold'] * item['sellingPrice']

        # Begin db transactions. First, create the day's record
        # engine = db.DatabaseWorker()._get_connection()
        # create_days_record_query = insert(self.daily_records_table).values(args)
        # with engine.connect() as connection:
        #     connection.execute(self.create_daily_record)
        #     connection.commit()
        conn = self._get_local_connection()
        cursor = conn.cursor()

        # Commit the record data
        query = "INSERT INTO daily_records (record_date, time_clocked_in, time_clocked_out, male_customers, female_customers, total_revenue, merchant_comments)" \
        "VALUES (%s, %s, %s, %s, %s, %s, %s)"

        cursor.execute(query, (recordDate, time_in, time_out, male_customers, female_customers, total_revenue, comments))
  
        # Child table. Obtain the autoincremented id value of this day
        new_records_id = cursor.lastrowid

        child_query = "INSERT INTO sales_item_record (record_id, item_name, product_signature, qty_sold, selling_price, rejects)" \
        "VALUES (%s, %s, %s, %s, %s, %s, %s)"

        for item in sales_items_list:
            cursor.execute(child_query, (new_records_id, item['name'], item['itemSignature'], item['qtySold'], item['sellingPrice'], 0))

        # Update the item's overview
        self.update_item_overview(cursor, deserialized_item_data=item)
        conn.commit()
        cursor.close()
        conn.close()

    def view_daily_records(self):
        pass

    def delete_record(self):
        pass

    def show_daily_performance_analysis(self):
        # Plot the graph based on total revenue per recorded day
        engine = db.DatabaseWorker()._get_connection()
        get_daily_performance_query = select(self.daily_records_table.c.record_date, self.daily_records_table.c.total_revenue)
        with engine.connect() as connection:
            rows = connection.execute(get_daily_performance_query).fetchall()

        if not rows:
            print("No rows were found!")

        # At this point, I have both dates and their respective revenues. Use a 2D matrix and sieve the data
        matrix = np.array(rows, dtype=object)

        dates = matrix[:, 0].astype(str)
        revenues = matrix[:, 1].astype(float)  

        plt.plot(dates, revenues, marker='o', color='green')
        plt.title("Daily performance June 2026")
        plt.xlabel("Record dates")
        plt.xticks(rotation=45)
        plt.ylabel("Revenue")
        plt.grid(visible=True)
        plt.show()

    def analyseCustomers(self):
        conn = db.DatabaseWorker()._get_connection()
        cursor = conn.cursor()
        query = "SELECT record_date, male_customers, female_customers FROM daily_records"
        cursor.execute(query)

        rows = cursor.fetchall()

        matrix = np.array(rows, dtype=object)
        record_dates = matrix[:, 0].astype(str)
        male_customers = matrix[:, 1].astype(int)
        female_customers = matrix[:, 2].astype(int)

        plt.plot(record_dates, male_customers, color='b', label="Male")
        plt.plot(record_dates, female_customers, color='red', label="Female")
        plt.ylabel("Customers")
        plt.xlabel("Record dates")
        plt.xticks(rotation=45)
        plt.grid(visible=True)
        plt.legend()
        plt.show()
if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("Insufficient arguments")

    action = sys.argv[1]
    data_args = sys.argv[2:]    

    recordmanager = DailyRecordsController()
    if action == "make_record_entry":
        recordmanager.create_daily_record(data_args)
    elif action == "show_daily_performance_graph":
        recordmanager.show_daily_performance_analysis()
    # dr = DailyRecordsController()
    # dr.show_daily_performance_analysis()
    # dr.show_daily_performance_analysis()