import pymysql
from db_config import mysql
from werkzeug.security import generate_password_hash, check_password_hash
from flask import Flask, request, jsonify

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
@app.route('/api/add', methods=['POST'])
def add():
    conn = mysql.connect()
    cursor = conn.cursor()
    json = request.get_json()
    print(json)
    StdEmpID = json['StdEmpID']
    Email = json['Email']
    Pwd = json['Pwd']
    FName = json['FName']
    LName = json['LName']
    if len(json['StdEmpID']) == 0:
        return jsonify({'error': 'invalid input'})
    else:
        #do not save password as a plain text
        #_hashed_password = generate_password_hash(_password)
        # save edits
        sql = "INSERT INTO tbl_user(StdEmpID, Email, Pwd, FName, LName) VALUES(%s, %s, %s, %s, %s)"
        data = (StdEmpID, Email, Pwd, FName, LName)
        cursor.execute(sql, data)
        conn.commit()
        resp = jsonify({'response':'User added Successfully'})
        resp.status_code = 200
        return resp
##    except Exception as e:
##        print(e)
##    finally:
##        cursor.close() 
##        conn.close()    
# running web app in local machine
if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
