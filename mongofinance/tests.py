"""
    Main test file. Should be splitted soon.
"""
from unittest import TestCase
from mongofinance.core.balance import Balance
from mongofinance.core.expense import Expense
from pyramid import testing
import pymongo
import datetime
from datetime import timedelta


class ViewTests(TestCase):

    """ Generic tests for all the views
    """

    def setUp(self):
        self.config = testing.setUp()
        self.request = testing.DummyRequest()
        #self.request.db = pymongo.Connection()['testing_db']

    def tearDown(self):
        testing.tearDown()

    def test_home_view(self):
        """
        Tests the / route
        """
        from mongofinance.views import home_view
        info = home_view(self.request)
        self.assertEquals(info, {})


class TestBalance(TestCase):

    """
    Bank balance tests
    """

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

    def test_near_future_balance(self):
        """
        Subtract all current unpaid expenses from the current balance to get the 'near future balance'.
        """
        expenses = [Expense("Expense-%s" % i, i) for i in range(1, 10)]
        balance = Balance(current=100, expenses=expenses)
        self.assertEqual(len(range(1, 10)), len(balance.expenses))
        self.assertEqual(
            100 - sum(range(1, 10)),
            balance.near_future
        )
        new_expense = Expense("Water", 100)
        expenses.append(new_expense)
        balance.add(new_expense)
        self.assertEqual(
            expenses,
            balance.expenses
        )
        self.assertEqual(
            100 - sum([expense.value for expense in expenses]),
            balance.near_future
        )
        print balance.near_future

    def test_expenses_side_effects(self):
        """
        Modifications made to expenses property shouldnt affect Balance!
        """
        expenses = [Expense("Expense-%s" % i, i) for i in range(1, 10)]
        balance = Balance(current=100, expenses=expenses)
        self.assertEqual(len(range(1, 10)), len(balance.expenses))

        expenses = balance.expenses
        expenses.append(Expense("Wont be appended",1000))
        self.assertNotEqual(len(expenses), len(balance.expenses))





class TestExpenses(TestCase):

    """
    Expenses tests
    """

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

    def test_recurring_expense_possible(self):
        """
        Repeat an expense based on 'repeat' attribute
        """
        today = datetime.date.today()
        expense = Expense("Water", 10,
                          date=today - timedelta(days=1))
        expense.repeat = True
        self.assertTrue(expense.is_recurrent(today.month, today.year))

        test_date = today + datetime.timedelta(weeks=100)
        self.assertTrue(expense.is_recurrent(test_date.month, test_date.year))

    def test_recurring_expense_until_end_date(self):
        """
        Given expense date and recurring end date,
        the recurrence function must validate the
        range between them.
        """
        today = datetime.date.today()
        expense = Expense("Water", 10,
                          date=today - timedelta(days=1))

        expense.repeat = True
        expense.recurring_end = today + timedelta(weeks=40)

        test_date = today + datetime.timedelta(weeks=100)
        self.assertFalse(expense.is_recurrent(test_date.month, test_date.year))

        test_date = today + datetime.timedelta(weeks=40)
        self.assertTrue(expense.is_recurrent(test_date.month, test_date.year))

        test_date = today + datetime.timedelta(weeks=50)
        self.assertFalse(expense.is_recurrent(test_date.month, test_date.year))
