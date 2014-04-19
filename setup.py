import os

from setuptools import setup, find_packages

here = os.path.abspath(os.path.dirname(__file__))
with open(os.path.join(here, 'README.md')) as f:
    README = f.read()
with open(os.path.join(here, 'CHANGES.txt')) as f:
    CHANGES = f.read()

requires = [
    'pyramid',
    'pyramid_chameleon',
    'pyramid_debugtoolbar',
    'pyramid_jinja2',
    'waitress',
    'pymongo',
    'nose',
    'coverage',
    'coveralls',
    ]

setup(name='MongoFinance',
      version='0.0',
      description='MongoFinance',
      long_description=README + '\n\n' + CHANGES,
      classifiers=[
        "Programming Language :: Python",
        "Framework :: Pyramid",
        "Topic :: Internet :: WWW/HTTP",
        "Topic :: Internet :: WWW/HTTP :: WSGI :: Application",
        ],
      author='Victor Shyba',
      author_email='victor.shyba@gmail.com',
      url='https://github.com/shyba/MongoFinance',
      keywords='personal finance manager web pyramid pylons',
      packages=find_packages(),
      include_package_data=True,
      zip_safe=False,
      install_requires=requires,
      tests_require=requires,
      test_suite="mongofinance",
      entry_points="""\
      [paste.app_factory]
      main = mongofinance:main
      """,
      )
