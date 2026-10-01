import sys
from scripts import database_worker

db = database_worker.DatabaseWorker()

if len(sys.argv) > 1:
    item_name = sys.argv[1]
    db.deleteItem(item_name)
