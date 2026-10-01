from scripts import database_worker
import sys

db = database_worker.DatabaseWorker()

if len(sys.argv) > 2:
    # cmd layout from Kotlin: python create_item.py item_name item_category item_bp item_qty
    item_name = sys.argv[1]
    item_category = sys.argv[2]
    item_bp = sys.argv[3]
    item_qty = sys.argv[4]
    db.createItemEntry(item_name, item_category, item_bp, item_qty)
    print(f"Recorded data: {item_name}, {item_category},{item_bp}, {item_qty}")
else:
    print("Insufficient data provided!")    