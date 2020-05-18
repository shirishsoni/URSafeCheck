# URSafeCheck
Harsh Patel
Vivek Pujara
Shirish Soni
Dipti Sanghvi

UR Safe Check is the application build for the Faculty of Science Department of the University of Regina. 
The main purpose of this application is to provide the SDS(Safety Data Sheets) for chemicals, Incident Reporting,
Risk Assesment and Analysis, Lab safety Manual, Emergency Contacts and many more.

To install & run this application on your device you have to perform the following steps:
1) Before installing the mobile application on your device you have to change the IP address in APIs file under the JAVA-> API folder.

2) You will need a server installed and listening. (Put your server and mobile device in the same network)

- For server and to listen to API calls you need the following software configured on your device.

    1) Python with Flask plugin
    Add following files to you flask server:
    These files are available under the JAVA->Rest2 folder of the application.
    - app.py
    - db_config.py
    - main.py

    2) Then you have to install Wamp server and have to add usersafechecktest.sql to your MYSQL.
    - userdafechecktest.sql file is available under JAVA->COM->EXAMPLE->URSAFECHECK folder.

3) After performing all the above steps you have to install the application on your mobile device.


NOTE: Certain Authentication features are unabled as the application is still under development.



