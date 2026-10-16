from scripts import database_worker as db
from datetime import datetime
import json
import numpy as np
import matplotlib.pyplot as plt

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

    def show_daily_performance_analysis(self):
        # Plot the graph based on total revenue per recorded day
        conn = db.DatabaseWorker()._get_connection()
        cursor = conn.cursor()
        query = "SELECT record_date, total_revenue FROM daily_records"
        cursor.execute(query)
        rows = cursor.fetchall()

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
    # if len(sys.argv) < 2:
    #     print("Insufficient arguments")

    # action = sys.argv[1]
    # data_args = sys.argv[2:]    

    # recordmanager = DailyRecordsController()
    # if action == "make_record_entry":
    #     recordmanager.create_daily_record(data_args)
    # elif action == "show_daily_performance_graph":
    #     recordmanager.show_daily_performance_analysis()
    dr = DailyRecordsController()
    dr.analyseCustomers()
    # dr.show_daily_performance_analysis()