import tkinter as tk
import subprocess
import os
import zipfile

import main as M

#Installer

#okay so this is the installer for the modpack.
#the rough idea is this script will be an exe in the end and if executed it will check if all required programs are installed.
#if not it will install them. (I think: fabric, java, MultiMc, and minecraft)
#if everything is installed it will check if the modpack is installed and on the latest version.
#if not it will install the modpack or update it.
#this should be doable with the modpack being uploaded to a github repo and then pulling any changes from there. (I hope)
#and if everything is installed and up to date it will just launch the modpack and the launcher as normal should start


#========================================================= MISC FUNCTIONS =========================================================#

def check_if_installed(program: str) -> bool:
    try:
        subprocess.run([program, "--version"], check=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        return True
    except (subprocess.CalledProcessError, FileNotFoundError):
        return False
    
def check_if_mmc_installed() -> bool:
    if not os.path.exists(M.location_mmc_dir): #check for MultiMC
        return False
    if not os.path.exists(M.location_mmc_exe): #check for exe
        return False
    return True #true if everything is installed

def check_if_modpack_installed() -> bool:
    if not os.path.exists(M.location_modpack_dir): #check for modpack
        return False
    if not os.path.exists(M.location_modpack_icon): #check for icon
        return False
    return True #true if everything is installed


#========================================================= INSTALLER FUNCTIONS =========================================================#

def useInstaller(installer_path: str) -> bool:
    if os.path.exists(installer_path):
        subprocess.run([installer_path, "/silent"], check=True)
        return True
    else:
        return False

def unpackZip(zip_path: str, destination: str) -> bool:
    if os.path.exists(zip_path):
        with zipfile.ZipFile(zip_path, 'r') as zip_ref:
            zip_ref.extractall(destination)
    else:
        return False

def cloneRepo(repo: str, destination: str) -> bool:
    if not os.path.exists(destination):
        subprocess.run(["git", "clone", "--branch", "modpack", repo, destination], check=True)
        return True
    else:
        return False

def copyFile(file: str, destination: str) -> bool:
    if os.path.exists(file): #check if file exists
        with open(file, 'rb') as f:
            with open(destination, 'wb') as d:
                d.write(f.read())
        return True
    else:
        return False


#========================================================= INSTALLER WINDOW =========================================================#

class InstallerWindow:
    def __init__(self, root):
        self.root = root
        self.root.title("Prominece II [Fighter] Installer")
        self.root.geometry("500x300")
        self.root.resizable(False, False)
        self.root.iconbitmap("icon.ico")

        # dark mode
        self.root.tk_setPalette(background='#333333', foreground='white', activeBackground='#666666', activeForeground='white')
        self.root.config(bg='#333333')

        self.frame = tk.Frame(self.root)
        self.frame.pack(padx=10, pady=10, fill=tk.BOTH, expand=True)

        self.context_label = tk.Label(self.frame, text="Hello, this is the installer for Prominence II [Fighter].", font=("Arial", 13))
        self.context_label.config(wraplength=470)
        self.context_label.pack()

        self.label = tk.Label(self.frame, text="This program will install all the required programs and files for the modpack.\n\nLater when the installation is complete, you can start the modpack by running the same file again, which is actually the right way to start the modpack.")
        self.label.config(wraplength=470)
        self.label.pack(pady=(0, 20))

        self.button_frame = tk.Frame(self.root)
        self.button_frame.pack(side=tk.BOTTOM, pady=(10, 10))

        self.start_button = tk.Button(self.button_frame, text="Start Modpack", command=self.start)
        self.button = tk.Button(self.button_frame, text="Start Installation", command=self.start_installation)

        self.button.pack(side=tk.RIGHT, padx=5)

        self.root.mainloop()


    #---------------- Window Functions ----------------#

    def message(self, text):
        self.label.config(text=text)
    
    def context(self, text):
        self.context_label.config(text=text)

    def error(self, exception):
        self.context("An error occured:")
        self.message(str(exception))
        self.button.config(text="Close", command=self.close)
        self.button.pack(side=tk.RIGHT, padx=5)

    def close(self):
        self.root.destroy()

    def start(self):
        #start the game
        M.debug("Starting Modpack...", 0.5)
        self.close()
        M.start_modpack()


    #---------------- Installing Process ----------------#
    
    def start_installation(self):
        #remove the button 
        self.button.pack_forget()
        self.context("Please be patient as this may take a while... (especially the first time).")
        self.label.config(text="") #clear the label without timeout

        #Java
        self.message("Checking for Java...")
        if not check_if_installed("java"):
            self.message("Java not found,\nInstalling...")
            useInstaller(M.path_java_installer)
            self.message("Java installed!")
        else:
            self.message("Java is already installed\nMoving on...")

        #JDK
        self.message("Checking for JDK 17...")
        if not check_if_installed("javac"):
            self.message("JDK 17 not found,\nInstalling...")
            useInstaller(M.path_jdk_installer)
            self.message("JDK 17 installed!")
        else:
            self.message("JDK 17 is already installed\nMoving on...")
        
        #Git
        self.message("Checking for Git...")
        if not check_if_installed("git"):
            self.message("Git not found,\nInstalling...")
            useInstaller(M.path_git_installer)
            self.message("Git installed!")
        else:
            self.message("Git is already installed\nMoving on...")

        #MultiMC
        self.message("Checking for MultiMC...")
        if not check_if_mmc_installed():
            self.message("MultiMC not found,\nInstalling...")
            unpackZip(M.path_mmc_zip, M.location_mmc_dir)
            self.message("MultiMC installed!")
        else:
            self.message("MultiMC is already installed\nMoving on...")

        self.message("Required programs are installed, now moving on to the modpack installation process...") #intermission

        #Modpack
        self.message("Checking for Modpack...")
        if not check_if_modpack_installed():
            self.message("Modpack not found,\nInstalling...")
            cloneRepo(M.modpack_repo, M.location_modpack_dir)
            copyFile(M.path_icon, M.location_modpack_icon)
            self.message("Modpack installed!")
        else:
            self.message("Modpack is already installed\nMoving on...")

        self.context("Installation complete!")
        self.message("The installation is complete, you can now start the modpack directly or by closing this window and running the file again.\n\n\nbefore I forget if MultiMC is installed for the first time it will ask some stuff generaly you can answer what you like but if it asks for a java version choose the one with jdk in the path and the version should be around 17.\nif you need help with anything else just ask me.")

        self.button.config(text="Close", command=self.close)

        self.start_button.pack(side=tk.LEFT, padx=5)
        self.button.pack(side=tk.RIGHT, padx=5)

#TODO: (maybe) I think mmc uses config files for settings. I could maybe make it so mmc doesn't prompt the user and instead I can make a custom dialog for the user to set the settings. (I think this is possible but it might go into the realm of overkill)