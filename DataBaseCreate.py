import sqlite3

conn=sqlite3.connect("dataBaseCourseApp.db")
cur=conn.cursor()

#Create table

#account Control table
cur.execute("CREATE TABLE IF NOT EXISTS ACCOUNT (id INTEGER PRIMARY KEY AUTOINCREMENT,\
            user VARCHAR(255),password VARCHAR(255))")

#profile Data table
cur.execute("CREATE TABLE IF NOT EXISTS PROFILE (id INTEGER PRIMARY KEY AUTOINCREMENT,\
            HOTEN VARCHAR(50),NGAYSINH DATE,GIOITINH VARCHAR(10),\
            MAIL VARCHAR(50),SCORE VARCHAR(50),PROCESS VARCHAR(50))")

#TESTING table
cur.execute("CREATE TABLE IF NOT EXISTS TESTING (id INTEGER PRIMARY KEY AUTOINCREMENT,\
            QUESTION VARCHAR(200),ANSKEY VARCHAR(50),ANSDUM1 VARCHAR(50),\
            ANSDUM2 VARCHAR(50),ANSDUM3 VARCHAR(50))")

#dummy
cur.execute("INSERT INTO ACCOUNT VALUES(null,'admin','admin')")
cur.execute("INSERT INTO ACCOUNT VALUES(null,'user1','user1')")
conn.commit()


#dummy
cur.execute("INSERT INTO PROFILE VALUES(null,'admin','1000-1-1','NAM','admin@admin','10','10')")
cur.execute("INSERT INTO PROFILE VALUES(null,'Le Tri Dung','1999-1-1','NAM','ltdung2699@gmail.com','10','10')")
conn.commit()

#dummy
cur.execute("INSERT INTO TESTING VALUES(null,'The head of the pigeon had been______ away with the rifle.',\
            'blown','blows','blow','blew')")
cur.execute("INSERT INTO TESTING VALUES(null,'Do you have _____ book focusing on this war?',\
            'any','some','the','None of the above')")
cur.execute("INSERT INTO TESTING VALUES(null,'I asked ____ but nobody had seen my wallet.',\
            'around','aside','any','about')")
cur.execute("INSERT INTO TESTING VALUES(null,'They ______ learning a new language next semester.',\
            'will be','will','will have','will have been')")
cur.execute("INSERT INTO TESTING VALUES(null,'The hit TV serial Sherlock has ultimately boiled _______ to a family drama and has disappointed many of its followers.',\
            'down','under','up','in')")
cur.execute("INSERT INTO TESTING VALUES(null,'It would be ______ historical moment when America finally has a female President.',\
            'a','an','the','what')")
cur.execute("INSERT INTO TESTING VALUES(null,'Several amendments were added to the club rules over the summer, but unfortunately ________ amendments did not promote inclusiveness.',\
            'the','an','a','is')")
cur.execute("INSERT INTO TESTING VALUES(null,'The report is due _____ Wednesday and hence I am procrastinating.',\
            'on','at','in','the')")
cur.execute("INSERT INTO TESTING VALUES(null,'In the tragic incident, none of the 145 passengers ____________.',\
            'survived','survive','is surviving','could survived')")
cur.execute("INSERT INTO TESTING VALUES(null,'Because love, it’s not ___ emotion. Love is a promise.',\
            'an','a','the','be')")



conn.commit()
conn.close()
