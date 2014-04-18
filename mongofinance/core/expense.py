"""
    Expense class and logic
"""
import datetime


class Expense(object):

    """
    This class represents an Expense
    """

    def __init__(self, name, value, date=None):
        self.repeat = None
        self.recurring_end = None
        self.name = name
        self.value = value
        if not date:
            self.date = datetime.date.today()
        else:
            self.date = date
        self.is_paid = False

    def is_recurrent(self, month, year):
        recurring_end = self.recurring_end
        if not recurring_end:
            return True
        test_date = datetime.date(month=month, year=year, day=recurring_end.day)
        delta = recurring_end - test_date
        print delta.days, test_date
        if delta.days >= 0:
            return True
        return False
