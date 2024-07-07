import subprocess
import os
import zipfile
import requests
import time

import main as M

def create_temp_folder():
    try:
        os.mkdir(M.location_temp)
    except FileExistsError:
        pass

def delete_temp_folder():
    try:
        os.rmdir(M.location_temp)
    except FileNotFoundError:
        pass

def install_file(url: str, local_filename: str): #install a file from an url in a temporary location
    try:
        with requests.get(url, stream=True) as r:
            r.raise_for_status()
            with open(local_filename, 'wb') as f:
                for chunk in r.iter_content(chunk_size=8192):
                    f.write(chunk)
    except Exception as e:
        raise e
    
#========================================================================================================================#

def check_if_installed(program: str) -> bool:
    try:
        subprocess.run([program, "--version"], check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        return True
    except (subprocess.CalledProcessError, FileNotFoundError):
        return False

def check_if_installed_mmc() -> bool: #check if MultiMc is installed
    #when cheching for MultiMc we need to check if the folder exists, the exe exists and the shortcut exists
    if os.path.exists(M.location_mmc_dir) and os.path.exists(M.location_mmc_exe) and os.path.exists(M.location_mmc_shortcut):
        return True
    else:
        return False
    
def check_if_installed_modpack() -> bool:
    #check if the modpack is installed
    if os.path.exists(M.location_modpack_dir) and os.path.exists(M.location_modpack_icon):
        return True
    else:
        return False

#========================================================================================================================#

def install_git():
    try:
        url = f"{M.url_git}/{M.installer_git}"
        local_filename = f"{M.location_temp}/{M.installer_git}"

        install_file(url, local_filename)
        subprocess.run([local_filename, "/NORESTART", "/NOCANCEL", "/VERYSILENT"], check=True)
        os.remove(local_filename)
    except Exception as e:
        print(f"Failed to install git: {e}")

def install_jdk():
    try:
        url = f"{M.url_jdk}/{M.installer_jdk}"
        local_filename = f"{M.location_temp}/{M.installer_jdk}"

        install_file(url, local_filename)
        subprocess.run([local_filename, "/s"], check=True)
        os.remove(local_filename)
    except Exception as e:
        print(f"Failed to install jdk: {e}")

def install_mmc():
    try:
        url = f"{M.url_mmc}/{M.installer_mmc}"
        local_filename = f"{M.location_temp}/{M.installer_mmc}"

        install_file(url, local_filename)
        #so now that we have the zip file we need to extract it but since it is a bit wierd... I'll explain,
        #so the Zipfile we just donwloaded, it has a folder called MultiMC in it and insde that folder is the actual MultiMC.exe, meaning instead of directly extracting the files we need to extract the folder and then move the files to the correct location
        #aka we extract the Folder also to the temp location and then move the files to the correct location
        with zipfile.ZipFile(local_filename, 'r') as zip_ref:
            zip_ref.extractall(M.location_temp)
        os.rename(f"{M.location_temp}/MultiMC", M.location_mmc_dir)

        #create a shortcut
        subprocess.run(["powershell", "-Command", f"$s=(New-Object -COM WScript.Shell).CreateShortcut('{M.location_mmc_shortcut}');$s.TargetPath='{M.location_mmc_exe}';$s.Save()"], check=True)

        #set basic settings
        #okay another thing we need to do is to set the basic settings for MultiMc, like the Java path and the languange
        #multimc stores settings in a file called multimc.cfg, so I'm not sure yet how mmc handles that file but let's just try to create a config file and just set the settings we need since mmc doesn't have the config file yet
        with open(f"{M.location_mmc_dir}/multimc.cfg", "w") as f:
            f.write("JavaPath=C:/Program Files/Java/jdk-17/bin/javaw.exe\n")
            f.write("Language=en_US\n")
            f.write(f"LastHostname={os.getenv('COMPUTERNAME')}\n")
            f.write("ShownNotifications=")

        os.remove(local_filename)

        #so the settings get applied we still need to start MultiMc once let it cope the settings and then close it prefferebly we don't want to distract the user so it would be nice to start it minimized
        #subprocess.Popen([M.location_mmc_exe], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        subprocess.Popen([M.location_mmc_exe], creationflags=subprocess.CREATE_NO_WINDOW, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        time.sleep(1)
        subprocess.run(["taskkill", "/IM", "MultiMC.exe", "/F"], check=True)
    except Exception as e:
        print(f"Failed to install MultiMc: {e}")


def install_modpack():
    #in order to install the modpack we need to clone the repository and download the icon.

    #first we download the icon since it's faster
    try:
        url = f"{M.url_icon}/{M.modpack_icon}"
        local_filename = f"{M.location_temp}/{M.modpack_icon}"

        install_file(url, local_filename)
        os.rename(local_filename, M.location_modpack_icon)
    except Exception as e:
        print(f"Failed to download icon: {e}")

    #now we can clone the repository
    try:
        subprocess.run(["git", "clone", "--branch", "modpack", M.modpack_repo, M.location_modpack_dir], check=True)
    except Exception as e:
        print(f"Failed to clone repository: {e}")
