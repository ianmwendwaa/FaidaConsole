from scripts import database_worker as db
from datetime import datetime
import json
import sys

class DailyRecordsController:
    def __init__(self):
        pass

    def create_daily_record(self, args):
        # conn = database_worker.DatabaseWorker()._get_connection()        
        if len(args) < 7:
            print(f"Expected 7 arguments but got {len(args)}") 

        str_date, time_in, time_out, male_customers, female_customers, comments, items_json_payload = args

        # Convert the date to a DATE since MySQL's complaining (Mismatching data types)
        recordDate = datetime.strptime(str_date, "%d/%m/%Y")
        # Parse the json payload to a list
        sales_items_list = json.loads(items_json_payload)
        print(sales_items_list)

        total_revenue = 0
        # Calculate the day's total revenue
        for item in sales_items_list:
            total_revenue += item['qtySold'] * item['sellingPrice']

        # Begin db transactions
        conn = db.DatabaseWorker()._get_connection()
        cursor = conn.cursor()

        # Parent table
        query = "INSERT INTO daily_records (record_date, time_clocked_in, time_clocked_out, male_customers, female_customers, total_revenue, merchant_comments)" \
        "VALUES (%s, %s, %s, %s, %s, %s, %s)"

        cursor.execute(query, (recordDate, time_in, time_out, male_customers, female_customers, total_revenue, comments))

        # Child table. Obtain the autoincremented id value of this day
        new_records_id = cursor.lastrowid

        child_query = "INSERT INTO sales_item_record (record_id, item_name, qty_sold, selling_price, rejects)" \
        "VALUES (%s, %s, %s, %s, %s)"

        for item in sales_items_list:
            cursor.execute(child_query, (new_records_id, item['name'], item['qtySold'], item['sellingPrice'], 0))
        conn.commit()
        cursor.close()
        conn.close()

    def view_daily_records(self):
        pass

    def delete_record(self):
        pass

if __name__ == '__main__':
    if len(sys.argv) < 2:
        print("Insufficient arguments")

    action = sys.argv[1]
    data_args = sys.argv[2:]    

    recordmanager = DailyRecordsController()
    if action == "make_record_entry":
        recordmanager.create_daily_record(data_args)