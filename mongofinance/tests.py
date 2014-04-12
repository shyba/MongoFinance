"""
    Main test file. Should be splitted soon.
"""
import unittest
from mongofinance.core.balance import Balance
from mongofinance.core.expense import Expense
from pyramid import testing
import pymongo
import datetime


class ViewTests(unittest.TestCase):

    def setUp(self):
        self.config = testing.setUp()
        self.request = testing.DummyRequest()
        self.request.db = pymongo.Connection()['testing_db']

    def tearDown(self):
        testing.tearDown()

    def test_home_view(self):
        """
        Tests the / route
        """
        from .views import home_view
        info = home_view(self.request)
        self.assertEquals(info, {})


class TestBalance(unittest.TestCase):

    def test_update_current_balance(self):
        """
            1:Update and view the current bank balance
        """
        balance = Balance(current=100)
        self.assertEquals(balance.current, 100)
        self.assertEquals(balance.initial, 0)
        balance.current = 50
        self.assertEquals(balance.current, 50)

    def test_initial_balance(self):
        """
            2:Set the initial balance as a fixed value (like your salary)
        """
        balance = Balance(initial=1000)
        self.assertEquals(balance.initial, 1000)


class TestExpenses(unittest.TestCase):

    def test_create_default_expense(self):
        """
            1:The expense will be added as unpaid.
            2:The expense will have an optional payment date. 
            When not specified, the current month will be used.
        """
        expense = Expense("Dinner", 100)
        self.assertFalse(expense.is_paid)
        today = datetime.date.today()
        self.assertEqual(expense.date, today)
