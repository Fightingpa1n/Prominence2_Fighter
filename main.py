import os
import sys
import ctypes
import subprocess
import time
import argparse

import window as W
import install as I


#okay I need to rewrite the installer from scratch

#========================================================= VALUES =========================================================#

local_path = sys._MEIPASS if hasattr(sys, '_MEIPASS') else os.path.abspath(".") #local path (this just allows me both the exported and the non exported version to work without changing the code)

#paths (included with the installer)
path_icon = os.path.join(local_path, "icon.ico") #bundeled icon

modpack_repo = "https://github.com/Fightingpa1n/Providence_EmberForge.git"
modpack_name = "Providence_EmberForge"
modpack_icon = "providence_icon.png"

#urls (you know what these are)
url_git = "https://github.com/git-for-windows/git/releases/download/v2.45.2.windows.1"
url_jdk = "https://download.oracle.com/java/17/latest"
url_mmc = "https://files.multimc.org/downloads"
url_icon = "https://github.com/Fightingpa1n/Providence_EmberForge/releases/download/icon"

installer_git = "Git-2.45.2-64-bit.exe"
installer_jdk = "jdk-17_windows-x64_bin.exe"
installer_mmc = "mmc-develop-win32.zip"

#make a temporary location for the files in the appdata folder
roaming = os.getenv("APPDATA")
local = os.getenv("LOCALAPPDATA")

location_temp = os.path.join(roaming, "_temp")

location_mmc_dir = os.path.join(local, "Programs/MultiMC")
location_mmc_exe = os.path.join(location_mmc_dir, "MultiMC.exe")
location_mmc_shortcut = os.path.join(roaming, "Microsoft/Windows/Start Menu/Programs/MultiMC.lnk")
location_modpack_dir = os.path.join(location_mmc_dir, f"instances/{modpack_name}")
location_modpack_icon = os.path.join(location_mmc_dir, f"icons/{modpack_icon}")

#========================================================= MAIN =========================================================#

def is_admin():
    try:
        return ctypes.windll.shell32.IsUserAnAdmin()
    except:
        return False

def run_as_admin():
    if not is_admin():
        print("aksing for admin rights")
        try:
            script = os.path.abspath(sys.argv[0])
            params = " ".join([script, "--install"])
            ctypes.windll.shell32.ShellExecuteW(None, "runas", sys.executable, params, None, 1)
        except Exception as e:
            print(f"Failed to get admin: {e}")
            sys.exit(1)
        sys.exit(0)

def start_modpack(): #start the modpack
    #check git
    print("Checking Modpack version...")
    os.chdir(location_modpack_dir)
    subprocess.run(["git", "fetch"], check=True)
    result = subprocess.run(["git", "status", "-uno"], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    if b"Your branch is up to date" in result.stdout:
        print("Modpack is up to date.", 0.5)
    else:
        print("Never Version Found. Updating...", 0.5)
        
        try:
            subprocess.run(["git", "pull"], check=True)
        except Exception:
            #discard any changes should there be any (there should be none aslong as the gitignore is airtight)
            subprocess.run(["git", "reset", "--hard"], check=True)
            subprocess.run(["git", "pull"], check=True)

        print("Modpack Updated.", 0.5)

    subprocess.Popen([location_mmc_exe, "--launch", f"{modpack_name}"])

    #indication it's starting
    for i in range(3):
        for p in range(3):
            os.system("cls")
            print("(please wait) Starting Modpack" + "."*p)
            time.sleep(1)

    print("the modpack should start, now, and this window should close. If it doesn't, you can close it manually.")
    sys.exit(0) #exit the thing

def check_if_everything_installed():
    git = I.check_if_installed("git")
    jdk = I.check_if_installed("javac") and I.check_if_installed("java")
    mmc = I.check_if_installed_mmc()
    modpack = I.check_if_installed_modpack()
    return git and jdk and mmc and modpack


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Runstaller")
    parser.add_argument("--install", action="store_true", help="Start the installer")
    parser.add_argument("--start", action="store_true", help="Start the modpack")

    args = parser.parse_args() #parse the arguments

    if not check_if_everything_installed() or args.install: #I will still add the check later
        run_as_admin()

        installer = W.InstallerWindow()
        installer.start()

        sys.exit(0)
    elif args.start:
        start_modpack()
    else:
        start_modpack() #if the modpack is already installed, just start it
    


