import json
from decimal import Decimal
from datetime import date

class DecimalEncorder(json.JSONEncoder):
    def default(self, obj):
        if isinstance(obj, Decimal):
            return float(obj)
        if isinstance(obj, date):
            return str(obj)
        return super(DecimalEncorder, self).default(obj)