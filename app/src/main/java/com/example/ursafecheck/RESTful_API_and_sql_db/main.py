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

ra_msg = " "

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
    StudentStaffid = json['id']
    occupation = json['occupation']
    dept = json['dept']
    date = json['date']
    time = json['time']
    building = json['building']
    room = json['room']
    incident = json['incident']
    details = json['details']
    initials = json['initials']
    employment = json['employment']
    treatment = json['treatment']   
    

    port = 465  # For SSL
    smtp_server = "smtp.gmail.com"

    #As of now our personal email is used but in the actual deployment
    #the email ID of the Safety advisor will be used
    sender_email = "shirishsoni128@gmail.com"  # Sender's email id
    password = "Shirish@123" #password for the email id
    #The content of the email
    message = "Subject: Incident Report Submitted \n\nFull Name: "+str(Fname)+\
              "\n\nAddress: "+str(address)+"\n\nCity: "+str(city)+"\n\nPostal Code: "+str(postal)+\
              "\n\nWork Phone: "+str(workPhone)+"\n\nPersonal Phone: "+str(personalPhone)+\
              "\n\nStudent or Staff ID: "+str(StudentStaffid)+"\n\nOccupation: "+str(occupation)+\
              "\n\nDepartment or Faculty: "+str(dept)+"\n\nEmployment Category: "+str(employment)+\
              "\n\nOccurence Date: "+str(date)+"\n\nIncident Time: "+str(time)+\
              "\n\nBuilding or off Campus Location: "+str(building)+"\n\nRoom Description or Room Number: "+str(room)+\
              "\n\nHow Incident Occured: "+str(incident)+"\n\nDetails of Injury and Treatment: "+str(details)+\
              "\n\nWas Medical Treatment Received: "+str(treatment)+"\n\nInitials: "+str(initials)

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

#Code for fetching Risk Assessment form1
@app.route('/api/raForm1', methods=['POST'])
def raForm1():
    
    conn = mysql.connect()
    cursor = conn.cursor()
    json = request.get_json()
    Ename = json['Ename']
    sup_name = json['sup_name']
    job_title = json['job_title']
    dept = json['dept']
    mob_no = json['mob_no']
    email = json['email']
    sup_email = json['sup_email']
    sup_no = json['sup_no']
    emo_no = json['emo_no']
    adv_name = json['adv_name']
    location = json['location']
    date = json['date']
    time = json['time']
    task = json['task']
    #rb1_ques = json['rb1_ques']
    rb1_value = json['rb1_value']
    #rb2_ques = json['rb2_ques']
    rb2_value = json['rb2_value']
    #rb3_ques = json['rb3_ques']
    rb3_value = json['rb3_value']
    #rb4_ques = json['rb4_ques']
    rb4_value = json['rb4_value']
    #rb5_ques = json['rb5_ques']
    rb5_value = json['rb5_value']
    #rb6_ques = json['rb6_ques']
    rb6_value = json['rb6_value']
    #rb7_ques = json['rb7_ques']
    rb7_value = json['rb7_value']
    #rb8_ques = json['rb8_ques']
    rb8_value = json['rb8_value']
    #rb9_ques = json['rb9_ques']
    rb9_value = json['rb9_value']
    #rb10_ques = json['rb10_ques']
    rb10_value = json['rb10_value']
    #rb11_ques = json['rb11_ques']
    rb11_value = json['rb11_value']
    #rb12_ques = json['rb12_ques']
    rb12_value = json['rb12_value']
    #rb13_ques = json['rb13_ques']
    rb13_value = json['rb13_value']
    #rb14_ques = json['rb14_ques']
    rb14_value = json['rb14_value']
    #rb15_ques = json['rb15_ques']
    rb15_value = json['rb15_value']
    #rb16_ques = json['rb16_ques']
    rb16_value = json['rb16_value']
    #rb17_ques = json['rb17_ques']
    rb17_value = json['rb17_value']
    #rb18_ques = json['rb18_ques']
    rb18_value = json['rb18_value']
    #rb19_ques = json['rb19_ques']
    rb19_value = json['rb19_value']
    #rb20_ques = json['rb20_ques']
    rb20_value = json['rb20_value']
    

    global ra_msg

    ra_msg = "Subject: Risk Assessment Report Submitted \n\nEmployee Name: "+str(Ename)+\
              "\n\nSupervisor Name: "+str(sup_name)+"\n\nJob Title: "+str(job_title)+"\n\nDepartment: "+str(dept)+\
              "\n\nUser mobile number: "+str(mob_no)+"\n\nUser Email: "+str(email)+\
              "\n\nSupervisor Email: "+str(sup_email)+"\n\n"+str(emo_no)+\
              "\n\n"+str(adv_name)+\
              "\n\n"+str(location)+"\n\nDate: "+str(date)+\
              "\n\nTime: "+str(time)+"\n\nTask: "+str(task)+\
              "\n\n 1) Are you working alone?" + "\n\n: "+str(rb1_value)+\
              "\n\n 2) Is the work area clear of all hazards?" +"\n\n: "+str(rb2_value)+\
              "\n\n 3) Am I authorized to perform the job?" + "\n\n: "+str(rb3_value)+\
              "\n\n 4) Do I have the correct tools or equipment for the job?" +"\n\n: "+str(rb4_value)+\
              "\n\n 5) Am I physically able to do the task?" +"\n\n: "+str(rb5_value)+\
              "\n\n 6) Do I have the correct PPE for the task?" +"\n\n: "+str(rb6_value)+\
              "\n\n 7) Are the conditions ideal to do the task?" + "\n\n: "+str(rb7_value)+\
              "\n\n 8) Do I feel comfortable about the task at hand?" +"\n\n: "+str(rb8_value)+\
              "\n\n 9) Have I discussed the task with others in the work area?" + "\n\n: "+str(rb9_value)+\
              "\n\n 10) Is my supervisor aware of what Task I am about to do?" +"\n\n: "+str(rb10_value)+\
              "\n\n 11) Are Driving conditions safe?" +"\n\n: "+str(rb11_value)+\
              "\n\n 12) Weather extremes:" + "\n\n: "+str(rb12_value)+\
              "\n\n 13) Biological:" + "\n\n: "+str(rb13_value)+\
              "\n\n 14) Chemical:" + "\n\n: "+str(rb14_value)+\
              "\n\n 15) Electrical: " +"\n\n: "+str(rb15_value)+\
              "\n\n 16) Fire: " +"\n\n: "+str(rb16_value)+\
              "\n\n 17) Falls: " +"\n\n: "+str(rb17_value)+\
              "\n\n 18) Lifting/Pushing: " +"\n\n: "+str(rb18_value)+\
              "\n\n 19) Compressed Gases/Vacuums: " +"\n\n: "+str(rb19_value)+\
              "\n\n 20) Heat: " +"\n\n: "+str(rb20_value)

              

    resp = jsonify({'response':'Page1 accepted'})
    resp.status_code = 200
    return resp
    cursor.close()
    conn.close()

#Code for submitting Hazards form
@app.route('/api/raForm2', methods=['POST'])
def raForm2():

    conn = mysql.connect()
    cursor = conn.cursor()
    json = request.get_json()
    hazards = json['hazards']
    hazards = hazards.replace('[', '')
    hazards = hazards.replace(']', '')
    hazards = hazards.replace('Hazard:', '\n\nHazard:')

    port = 465  # For SSL
    smtp_server = "smtp.gmail.com"

    #As of now our personal email is used but in the actual deployment
    #the email ID of the Safety advisor will be used
    sender_email = "shirishsoni128@gmail.com"  # Sender's email id
    password = "Shirish@123" #password for the email id
    #The content of the email

    global ra_msg
    ra_msg = ra_msg + str(hazards)

    #Sending the email
    context = ssl.create_default_context()
    with smtplib.SMTP_SSL(smtp_server, port, context=context) as server:
        server.login(sender_email, password)
        server.sendmail(sender_email, "shirishsoni12@gmail.com", ra_msg)
        
    resp = jsonify({'response':'Risk Assessment Form submitted'})
    resp.status_code = 200
    return resp
    cursor.close()
    conn.close()
    
    
# running web app in local machine
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
   



    
