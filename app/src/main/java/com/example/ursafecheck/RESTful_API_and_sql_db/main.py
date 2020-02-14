#importing the mysql connector which was initialized in dbconfig file
from db_config import mysql

#Package for hashing the password before updating the password in the database
from werkzeug.security import generate_password_hash, check_password_hash

#Package for handling json objects & API requests from the android application
from flask import Flask, request, jsonify

#For generating rnadom password as One time password(OTP)
from random import randint

#For email
import smtplib, ssl

app = Flask(__name__)

# Different functions have been created for different requests
# that the android application sends to the RESTful API
# Each request comes through a separate route, thus, each
# function has been defined for a specific route(URL)

# root, the basic route, just for testing
@app.route("/")
#The function to be executed upon this request
def index():
    """
    this is a root dir of my server
    :return: str
    """
    return "This is root!!!!"

# Adding the new user
@app.route('/api/add', methods=['POST'])
def add():
    # Creating a mysql connection request
    conn = mysql.connect()
    #Cursor to execute the query
    cursor = conn.cursor()
    #Grabbing the JSON object from the request send by Android app
    json = request.get_json()
    #fetching values from the JSON object using the ID of value
    StdEmpID = json['StdEmpID']
    Email = json['Email']
    FName = json['FName']
    LName = json['LName']
    #Checking for empty value in JSON object
    if len(json['StdEmpID']) == 0:
        return jsonify({'error': 'invalid input'})
    else:
        #Generating random value for password
        Pwd = randint(100000,999999)

        #Configuring SMTP and SSL for email
        port = 465  # For SSL
        smtp_server = "smtp.gmail.com"

        #As of now our personal email is used but in the actual deployment
        #the email ID of the Safety advisor will be used
        sender_email = "shirishsoni128@gmail.com"  # Sender's email id
        password = "Shirish@123" #password for the email id
        #The content of the email
        message = "Subject: OTP for URSafeCheck \n\nHere is your one time password:"+str(Pwd)

        #Sending the email
        context = ssl.create_default_context()
        with smtplib.SMTP_SSL(smtp_server, port, context=context) as server:
            server.login(sender_email, password)
            server.sendmail(sender_email, Email, message)

        #Hashing the password so that it is not saved as a plain text in te database
        hashed_password = generate_password_hash(str(Pwd))

        # Generating insert query for adding new user
        sql = "INSERT INTO tbl_user(StdEmpID, Email, Pwd, FName, LName) VALUES(%s, %s, %s, %s, %s)"
        data = (StdEmpID, Email, hashed_password, FName, LName)

        #Executing the query
        cursor.execute(sql, data)
        conn.commit()

        #Sending JSON response
        resp = jsonify({'response':'User added Successfully'})
        resp.status_code = 200
        return resp
    #Closing the cursor and connection to the database
    cursor.close() 
    conn.close()


# Code for Login
@app.route('/api/login', methods=['POST'])
def login():
    # Creating a mysql connection request
    conn = mysql.connect()
    # Cursor to execute the query
    cursor = conn.cursor()
    # Grabbing the JSON object from the request send by Android app
    json = request.get_json()
    # fetching values from the JSON object using the ID of value
    Email = json['Email']
    Pwd = json['Pwd']

    # Checking for empty value in JSON object
    if len(json['Email']) == 0:
        return jsonify({'error': 'invalid input'})
    else:
        #Generating SQL query for fetching the password and first login
        # value for the entered email id from the database
        sql = "SELECT Pwd,FirstLogin FROM tbl_user WHERE Email=%s"
        data = (Email)

        # Executing the query
        cursor.execute(sql,data)

        #Extracting the password from the fetched row
        row = cursor.fetchone()

        #Checking if the password matches and first login is false
        if check_password_hash(row[0],Pwd) and row[1]==0:
            resp = jsonify({'response':'1'})
        # Checking if the password matches and first login is true
        elif check_password_hash(row[0],Pwd) and row[1]==1:
            resp = jsonify({'response':'2'})
        # Checking if the password does not match
        elif check_password_hash(row[0],Pwd)== False:
            resp = jsonify({'response':'3'})
        # If username not found
        else:
            resp = jsonify({'response':'4'})
        # returning the response
        return resp

    # Closing the cursor and connection to the database
    cursor.close()
    conn.close()

# Code for forget password request
@app.route('/api/forgot', methods=['POST'])
def forgot():
    # Creating a mysql connection request
    conn = mysql.connect()
    # Cursor to execute the query
    cursor = conn.cursor()
    # Grabbing the JSON object from the request send by Android app
    json = request.get_json()
    # fetching values from the JSON object using the ID of value
    Email = json['Email']

    # Checking for empty value in JSON object
    if len(json['Email']) == 0:
        return jsonify({'error': 'invalid input'})
    else:
        # Generating random value for password
        Pwd = randint(100000, 999999)

        # Configuring SMTP and SSL for email
        port = 465  # For SSL
        smtp_server = "smtp.gmail.com"

        # As of now our personal email is used but in the actual deployment
        # the email ID of the Safety advisor will be used
        sender_email = "shirishsoni128@gmail.com"  # Sender's email id
        password = "Shirish@123"  # password for the email id
        # The content of the email
        message = "Subject: OTP for URSafeCheck \n\nHere is your one time password:" + str(Pwd)

        context = ssl.create_default_context()
        #Sending the email
        with smtplib.SMTP_SSL(smtp_server, port, context=context) as server:
            server.login(sender_email, password)
            server.sendmail(sender_email, Email, message)

        #Hashing the password so that it is not saved as a plain text in te database
        hashed_password = generate_password_hash(str(Pwd))


        # Generating update query for reseting the password
        sql = "UPDATE tbl_user SET Pwd= %s,FirstLogin=1 WHERE Email=%s"
        data = (hashed_password, Email)

        #Executing the query
        cursor.execute(sql, data)
        conn.commit()

        #Responding to the Android app with JSON object
        resp = jsonify({'response': 'OTP sent Successfully'})
        resp.status_code = 200
        return resp

    # Closing the cursor and connection to the database
    cursor.close()
    conn.close()

# Code Reset Password
@app.route('/api/ResetPwd', methods=['POST'])
def ResetPwd():

    # Creating a mysql connection request
    conn = mysql.connect()
    # Cursor to execute the query
    cursor = conn.cursor()
    # Grabbing the JSON object from the request send by Android app
    json = request.get_json()
    Email = json['Email']
    Pwd = json['Pwd']

    # Checking for empty value in JSON object
    if len(Pwd) == 0 or len(Email) == 0:
        return jsonify({'error': 'invalid input'})
    else:
        # Hashing the password so that it is not saved as a plain text in te database
        hashed_password = generate_password_hash(str(Pwd))

        # Generating update query for reseting the password
        sql = "UPDATE tbl_user SET Pwd= %s,FirstLogin=0 WHERE Email=%s"
        data = (hashed_password, Email)

        # Executing the query
        cursor.execute(sql, data)
        conn.commit()

        # Responding to the Android app with JSON object
        resp = jsonify({'response': 'Password updated Successfully'})
        resp.status_code = 200
        return resp

    # Closing the cursor and connection to the database
    cursor.close()
    conn.close()

#Code for submitting Incident Reporting form
@app.route('/api/irForm', methods=['POST'])
def irForm():

    conn = mysql.connect()
    cursor = conn.cursor()
    json = request.get_json()
    Fname = json['Fname']
    address = json['address']
    city = json['city']
    postal = json['postal']
    workPhone = json['workPhone']
    personalPhone = json['personalPhone']

    port = 465  # For SSL
    smtp_server = "smtp.gmail.com"

    #As of now our personal email is used but in the actual deployment
    #the email ID of the Safety advisor will be used
    sender_email = "shirishsoni128@gmail.com"  # Sender's email id
    password = "Shirish@123" #password for the email id
    #The content of the email
    message = "Subject: Incident Report Submitted \n\nFull Name:"+str(Fname)+"\n\nAddress:"+str(address)+"\nCity:"+str(city)+"\nPostal Code:"+str(postal)+"\nWork Phone:"+str(workPhone)+"\nPersonal Phone:"+str(personalPhone)

    #Sending the email
    context = ssl.create_default_context()
    with smtplib.SMTP_SSL(smtp_server, port, context=context) as server:
        server.login(sender_email, password)
        server.sendmail(sender_email, "shirishsoni12@gmail.com", message)
    resp = jsonify({'response':'User added Successfully'})
    resp.status_code = 200
    return resp
    cursor.close()
    conn.close()

# running web app in local machine
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
