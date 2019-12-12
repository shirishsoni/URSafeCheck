from app import app
#Importing MySql connector from the Flask package
from flaskext.mysql import MySQL

#Creating an instance of MySql connector 
mysql = MySQL()

#MySQL configuration
app.config['MYSQL_DATABASE_USER'] = 'root' #Username for MySql(WAMP)
app.config['MYSQL_DATABASE_PASSWORD'] ='' #Password for MySql(WAMP)
app.config['MYSQL_DATABASE_DB'] = 'ursafechecktest' #Name of database
app.config['MYSQL_DATABASE_HOST'] = 'localhost' #Host address of the WAMP server

mysql.init_app(app) #Initiating app

