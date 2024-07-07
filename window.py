import tkinter as tk
import sys
import os

import main as M
import install as I

#settings
title = "Installer"
size = "300x300"
timeout = 200


class InstallerWindow:
    def __init__(self):
        self.root = tk.Tk() #create root
        self.root.geometry(size) #set size
        self.root.title(title) #set title

        self.root.iconbitmap(M.path_icon) #set icon
        self.root.resizable(False, False) #disable resizing
        self.root.tk_setPalette(background='#333333', foreground='white', activeBackground='#666666', activeForeground='white') #dark mode

        #Actual window stuff
        main_frame = tk.Frame(self.root) #contains everything
        text_frame = tk.Frame(main_frame) #contains the text thingys
        self.button_frame = tk.Frame(main_frame) #contains the buttons

        #context title
        self.context_title = tk.Label(text_frame, text="")

        #text box
        self.text = tk.Text(text_frame, height=10, width=50)
        self.scroll = tk.Scrollbar(text_frame, command=self.text.yview)
        self.text.config(yscrollcommand=self.scroll.set)
        self.text.config(state=tk.DISABLED)

        #pack everything
        main_frame.pack(padx=10, pady=10, fill=tk.BOTH, expand=True)
        text_frame.pack()
        self.button_frame.pack()
        self.context_title.pack()
        self.text.pack(side=tk.LEFT)
        self.scroll.pack(side=tk.RIGHT, fill=tk.Y)

        #start the window
        self.init()
        self.just_installed_git = False
        self.root.mainloop()

    #================ MISC FUNCTIONS ================#

    def set_context(self, text):
        self.context_title.config(text=text)

    def set_text(self, text, print_=False):
        self.text.config(state=tk.NORMAL)
        self.text.delete(1.0, tk.END)
        self.text.insert(tk.END, text)
        self.text.config(state=tk.DISABLED)
        if print_:
            print(text)
    
    def close_window(self):
        self.root.quit()
        self.root.destroy()


    #================ MAIN FUNCTIONS ================#

    def init(self):
        #set texts
        self.set_context("Hello, this is the installer for Providence Ember Forge.")
        self.set_text("This program will install all the required programs and files for the modpack.\n\nLater when the installation is complete, you can start the modpack by running the same file again, which is actually the right way to start the modpack.")

        #buttons
        self.install_button = tk.Button(self.button_frame, text="Install", command=self.start_install)
        self.cancel_button = tk.Button(self.button_frame, text="Cancel", command=self.cancel)

        #pack buttons
        self.install_button.pack(side=tk.LEFT)
        self.cancel_button.pack(side=tk.RIGHT)


    def cancel(self):
        print("canceling Installation")
        self.close_window()

    def start(self):
        print("Starting Modpack")
        self.root.quit()
        self.root.destroy()
        M.start_modpack()

    def start_install(self):
        #remove buttons
        I.create_temp_folder()
        self.install_button.pack_forget()
        self.cancel_button.pack_forget()

        #start the installation
        self.set_context("Installation started")
        self.set_text("Checking for Programs...")
        self.root.after(200, self.check, "Git")


    def check(self, program="Git"):
        #check if a program is installed
        print(f"Checking for {program}...")

        if program == "Git": #check for git
            self.set_context("Git")
            if I.check_if_installed("git"):
                self.set_text("Git is installed", True)
                self.root.after(timeout, self.check, "Jdk")
            else:
                self.root.after(timeout, self.install, "Git")

        elif program == "Jdk": #check for jdk
            self.set_context("Jdk/Java")
            if I.check_if_installed("javac") and I.check_if_installed("java"):
                self.set_text("Jdk is installed", True)
                self.root.after(timeout, self.check, "mmc")
            else:
                self.root.after(timeout, self.install, "Jdk")
            
        elif program == "mmc": #check for MultiMc
            self.set_context("MultiMc")
            if I.check_if_installed_mmc():
                self.set_text("MultiMc is installed", True)
                self.root.after(timeout, self.check, "modpack")
            else:
                self.root.after(timeout, self.install, "mmc")

        elif program == "modpack":
            self.set_context("Modpack")
            if I.check_if_installed_modpack():
                self.set_text("Modpack is installed", True)
                self.root.after(timeout, self.end_install)
            else:

                if not self.just_installed_git:
                    self.root.after(timeout, self.install, "modpack")
                else:
                    self.set_text("Since git was just installed, you need to restart the installer to install the modpack\nit will automatically close in 5 secconds", True)
                    self.root.after(5000, self.close_window)


    def install(self, program="Git"):
        #now we install stuff
        if program == "Git":
            self.set_context("Git")
            self.set_text("Git not found,\nInstalling...", True)
            self.root.after(timeout, I.install_git)
            self.root.after(timeout, self.set_text, "Git has been installed", True)
            self.just_installed_git = True
            self.root.after(timeout*2, self.check,"Jdk")

        if program == "Jdk":
            self.set_context("Jdk/Java")
            self.set_text("Jdk not found,\nInstalling...", True)
            self.root.after(timeout, I.install_jdk)
            self.root.after(timeout, self.set_text, "Jdk has been installed", True)
            self.root.after(timeout*2, self.check, "mmc")
        
        if program == "mmc":
            self.set_context("MultiMc")
            self.set_text("MultiMc not found,\nInstalling...", True)
            self.root.after(timeout, I.install_mmc)
            self.root.after(timeout, self.set_text, "MultiMc has been installed", True)
            self.root.after(timeout*2, self.check, "modpack")

        if program == "modpack":
            self.set_context("Modpack")
            self.set_text("Modpack not found,\nInstalling...", True)
            self.root.after(timeout, I.install_modpack)
            self.root.after(timeout, self.set_text, "Modpack has been installed", True)
            self.root.after(timeout*2, self.end_install)

    def end_install(self):
        I.delete_temp_folder()
        self.set_context("Installation Complete")
        self.set_text("You can now start the modpack.", True)

        self.install_button.config(text="Start Modpack", command=self.start)
        self.cancel_button.config(text="Close", command=self.close_window)
        self.install_button.pack(side=tk.LEFT)
        self.cancel_button.pack(side=tk.RIGHT)




