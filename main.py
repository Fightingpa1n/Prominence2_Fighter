import subprocess
import os
import time
import tkinter as tk
import sys

import installer as I

#the main file for the modpack running or installing

#========================================================= VALUES =========================================================#

#modpack info
modpack_name = "Providence_EmberForge" #this is the name of the Instance in MultiMC and the Repo name on github
modpack_repo = "https://github.com/Fightingpa1n/Providence_EmberForge.git" #this is the github repo where the modpack is stored

#paths
local_path = sys._MEIPASS if hasattr(sys, '_MEIPASS') else os.path.abspath(".") #local path

path_git_installer = os.path.join(local_path, "data/git_installer.exe") #bundeled git installer
path_java_installer = os.path.join(local_path, "data/java_installer.exe") #bundeled java installer
path_jdk_installer = os.path.join(local_path, "data/jdk_17_installer.exe") #bundeled jdk installer
path_mmc_zip = os.path.join(local_path, "data/mmc.zip") #bundeled MultiMC zip
path_icon = os.path.join(local_path, "data/providence_icon.png") #bundeled icon
path_app_icon = os.path.join(local_path, "icon.ico") #bundeled app icon
path_mmc_shortcut = os.path.join(local_path, "data/MultiMC.lnk") #bundeled MultiMC shortcut

location_mmc_dir = os.path.join(os.getenv("LOCALAPPDATA"), "Programs/MultiMC")
location_mmc_exe = os.path.join(location_mmc_dir, "MultiMC.exe")
location_modpack_dir = os.path.join(location_mmc_dir, f"instances/{modpack_name}")
location_modpack_icon = os.path.join(location_mmc_dir, f"icons/providence_icon.png")
location_mmc_shortcut = os.path.join(os.getenv("APPDATA"), "Microsoft/Windows/Start Menu/Programs/MultiMC.lnk")

#========================================================= MISC FUNCTIONS =========================================================#

def check_if_everything_is_installed() -> bool:
    #check if everything is installed
    if not I.check_if_installed("java"): #check for java
        return False
    if not I.check_if_installed("javac"): #check for jdk
        return False
    if not I.check_if_installed("git"): #check for git
        return False
    if not I.check_if_mmc_installed(): #check for multimc
        return False
    if not I.check_if_modpack_installed(): #check for modpack
        return False
    return True #true if everything is installed
    
def start_modpack(): #start the modpack
    #check git
    debug("Checking Modpack version...")
    os.chdir(location_modpack_dir)
    subprocess.run(["git", "fetch", "origin", "modpack"], check=True)
    result = subprocess.run(["git", "status", "-uno"], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    if b"Your branch is up to date" in result.stdout:
        debug("Modpack is up to date.", 0.5)
    else:
        debug("Never Version Found. Updating...", 0.5)
        
        try:
            subprocess.run(["git", "pull"], check=True)
        except Exception:
            #discard any changes should there be any (there should be none aslong as the gitignore is airtight)
            subprocess.run(["git", "reset", "--hard"], check=True)
            subprocess.run(["git", "pull"], check=True)

        debug("Modpack Updated.", 0.5)

    subprocess.Popen([location_mmc_exe, "--launch", f"{modpack_name}"])

    #indication it's starting
    for i in range(3):
        debug("(please wait) Starting Modpack")
        debug("(please wait) Starting Modpack.")
        debug("(please wait) Starting Modpack..")
        debug("(please wait) Starting Modpack...")

    debug("the modpack should start, now, and this window should close. If it doesn't, you can close it manually.", 0.1)
    sys.exit() #exit the thing

def debug(msg: str, duration = 1):
    os.system("cls")
    print(msg)
    time.sleep(duration)


#========================================================= MAIN PROCESS =========================================================#

if __name__ == "__main__":
    #installer
    debug("searching for required Programs...", 0.5)
    if not check_if_everything_is_installed():
        debug("not all Programs have been detected. Starting Installer...")
        window = I.InstallerWindow(tk.Tk())
    else: #start the modpack
        debug("All Programs have been detected. Starting Modpack...", 0.5)
        start_modpack()
