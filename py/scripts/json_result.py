from decimal import Decimal
import json
from datetime import date

class DecimalEncoder(json.JSONEncoder):
    def default(self, o):
        if isinstance(o, Decimal):
            return str(o)
        if isinstance(o, date):
            return str(o)
        return super().default(o)