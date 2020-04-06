from tkinter import *
from datetime import datetime
import pandas as pd
import os

memo = ""

def refresh():

    global memo

    df = read_lines()
    lbl = Label(window, text="Last alerts from Presta Cop drones")
    lbl.grid(column=0, row=0)
    lbl1 = Label(window, text=datetime.now().strftime("%d/%m/%Y %H:%M:%S"))
    lbl1.grid(column=0, row=1)
    lbl1 = Label(window, text=df.tail(10))
    lbl1.grid(column=0, row=2)
    if memo != str(df.tail(10)):
        memo = str(df.tail(10))
        os.system('spd-say "you have a new alert"')
    window.after(5000, refresh)

def read_lines():
    with open("alert_log_file.csv") as file:
        df = pd.read_csv(file, header=None)
        df.set_axis(["summons", "plate", "state", "date", "time", "violation", "car make", "latitude", "longitude", "battery", "drone id."], axis=1, inplace=True)
        datetime = []
        for date, time in zip(df.date, df.time):
            datetime.append(date + " " + time)
        df["datetime"] = datetime
        pd.to_datetime(df.datetime)
        #df.sort_values(by='datetime', inplace=True)
        df.drop(axis=1, labels=["summons", "date", "time", "violation", "battery"], inplace=True)
        return df


read_lines()

window = Tk()
window.title("Presta Cop -Alert")

refresh()

window.mainloop()