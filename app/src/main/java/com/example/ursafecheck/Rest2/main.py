import pymysql
from db_config import mysql
from werkzeug.security import generate_password_hash, check_password_hash
from flask import Flask, request, jsonify
from random import randint
import smtplib, ssl

app = Flask(__name__)

# root
@app.route("/")
def index():
    """
    this is a root dir of my server
    :return: str
    """
    return "This is root!!!!"

# GET
@app.route('/users/<user>')
def hello_user(user):
    """
    this serves as a demo purpose
    :param user:
    :return: str
    """
    return "Hello %s!" % user

# POST
@app.route('/api/post_some_data', methods=['POST'])
def get_text_prediction():
    """
    predicts requested text whether it is ham or spam
    :return: json
    """
    json = request.get_json()
    print(json)
    if len(json['text']) == 0:
        return jsonify({'error': 'invalid input'})

    return jsonify({'you sent this': json['text']})


# Adding the new user
@app.route('/api/add', methods=['POST'])
def add():
    conn = mysql.connect()
    cursor = conn.cursor()
    json = request.get_json()
    StdEmpID = json['StdEmpID']
    Email = json['Email']
    FName = json['FName']
    LName = json['LName']
    if len(json['StdEmpID']) == 0:
        return jsonify({'error': 'invalid input'})
    else:
        Pwd = randint(100000,999999)
        port = 465  # For SSL
        smtp_server = "smtp.gmail.com"
        sender_email = "shirishsoni128@gmail.com"  # Enter your address
        password = "Shirish@123"
        message = "Subject: OTP for URSafeCheck \n\nHere is your one time password:"+str(Pwd)

        context = ssl.create_default_context()
        with smtplib.SMTP_SSL(smtp_server, port, context=context) as server:
            server.login(sender_email, password)
            server.sendmail(sender_email, Email, message)
        #do not save password as a plain text
        hashed_password = generate_password_hash(str(Pwd))
        # save edits
        sql = "INSERT INTO tbl_user(StdEmpID, Email, Pwd, FName, LName) VALUES(%s, %s, %s, %s, %s)"
        data = (StdEmpID, Email, hashed_password, FName, LName)
        cursor.execute(sql, data)
        conn.commit()
        resp = jsonify({'response':'User added Successfully'})
        resp.status_code = 200
        return resp
    cursor.close()
    conn.close()


# Login
@app.route('/api/login', methods=['POST'])
def login():
    conn = mysql.connect()
    cursor = conn.cursor()
    json = request.get_json()
    Email = json['Email']
    Pwd = json['Pwd']
    if len(json['Email']) == 0:
        return jsonify({'error': 'invalid input'})
    else:
        sql = "SELECT Pwd,FirstLogin FROM tbl_user WHERE Email=%s"
        data = (Email)
        cursor.execute(sql,data)
        row = cursor.fetchone()
        print(check_password_hash(str(row[0]),str(Pwd)))
        if check_password_hash(row[0],Pwd) and row[1]==0:
            resp = jsonify({'response':'1'})

        elif check_password_hash(row[0],Pwd) and row[1]==1:
            resp = jsonify({'response':'2'})

        elif check_password_hash(row[0],Pwd)== False:
            resp = jsonify({'response':'3'})

        else:
            resp = jsonify({'response':'4'})
        return resp
    cursor.close()
    conn.close()

# Forgot
@app.route('/api/forgot', methods=['POST'])
def forgot():
    conn = mysql.connect()
    cursor = conn.cursor()
    json = request.get_json()
    Email = json['Email']
    if len(json['Email']) == 0:
        return jsonify({'error': 'invalid input'})
    else:
        Pwd = randint(100000, 999999)
        port = 465  # For SSL
        smtp_server = "smtp.gmail.com"
        sender_email = "shirishsoni128@gmail.com"  # Enter your address
        password = "Shirish@123"
        message = "Subject: OTP for URSafeCheck \n\nHere is your one time password:" + str(Pwd) + "\n This OTP expires in 15 mins"

        context = ssl.create_default_context()
        with smtplib.SMTP_SSL(smtp_server, port, context=context) as server:
            server.login(sender_email, password)
            server.sendmail(sender_email, Email, message)

        # do not save password as a plain text
        hashed_password = generate_password_hash(str(Pwd))
        # save edits
        sql = "UPDATE tbl_user SET Pwd= %s,FirstLogin=1 WHERE Email=%s"
        data = (hashed_password, Email)
        i = cursor.execute(sql, data)
        print(i)
        conn.commit()
        resp = jsonify({'response': 'OTP sent Successfully'})
        resp.status_code = 200
        return resp
    cursor.close()
    conn.close()

# Reset Password
@app.route('/api/ResetPwd', methods=['POST'])
def ResetPwd():
    conn = mysql.connect()
    cursor = conn.cursor()
    json = request.get_json()
    Email = json['Email']
    Pwd = json['Pwd']
    if len(Pwd) == 0 or len(Email) == 0:
        return jsonify({'error': 'invalid input'})
    else:
        # do not save password as a plain text
        hashed_password = generate_password_hash(str(Pwd))
        # save edits
        sql = "UPDATE tbl_user SET Pwd= %s,FirstLogin=0 WHERE Email=%s"
        data = (hashed_password, Email)
        i = cursor.execute(sql, data)
        print(i)
        conn.commit()
        resp = jsonify({'response': 'Password updated Successfully'})
        resp.status_code = 200
        return resp
    cursor.close()
    conn.close()


# running web app in local machine
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
