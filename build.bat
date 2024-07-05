:: SO now for real, this is the build script for the installer, and what it does is just run the pyinstaller command to compile the installer.py file into a single executable file.
:: the main file is called main.py, and it requires the installer.py file alongside to work.
:: as for additonal data we have the data folder that contains the following: git_installer.exe, java_installer.exe, jdk_17_installer.exe, mmc.zip, prominence_icon.png
:: lastly we also want a few additional stuff to be set for example the name of the executable and the icon, so we set those as well.

pyinstaller --onefile --add-data "data/git_installer.exe;." --add-data "data/java_installer.exe;." --add-data "data/jdk_17_installer.exe;." --add-data "data/mmc.zip;." --add-data "data/MultiMC.lnk;." --add-data "data/providence_icon.png;." --add-data "icon.ico;." main.py --icon=icon.ico --name="ProvidenceEmberForge"
