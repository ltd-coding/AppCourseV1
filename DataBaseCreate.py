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

#dummy
cur.execute("INSERT INTO ACCOUNT VALUES(null,'admin','admin')")
cur.execute("INSERT INTO ACCOUNT VALUES(null,'user1','user1')")
conn.commit()


#dummy
cur.execute("INSERT INTO PROFILE VALUES(null,'admin','1000-1-1','NAM','admin@admin','10','10')")
cur.execute("INSERT INTO PROFILE VALUES(null,'Le Tri Dung','1999-1-1','NAM','ltdung2699@gmail.com','10','10')")
conn.commit()

conn.close()

