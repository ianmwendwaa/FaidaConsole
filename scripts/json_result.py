import json
from decimal import Decimal
from datetime import datetime

class DecimalEncorder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, Decimal):
            return float(obj)
        if isinstance(obj, datetime):
            return str(obj)
        return super(DecimalEncorder, self).default(obj)