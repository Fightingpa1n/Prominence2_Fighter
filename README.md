# Prominence II [Fighter] Installer

okay so this is the installer for the modpack.
the rough idea is this script will be an exe in the end and if executed it will check if all required programs are installed.
if not it will install them. (I think: fabric, java, MultiMc, and minecraft)
if everything is installed it will check if the modpack is installed and on the latest version.
if not it will install the modpack or update it.
this should be doable with the modpack being uploaded to a github repo and then pulling any changes from there. (I hope)
and if everything is installed and up to date it will just launch the modpack and the launcher as normal should start

also of note since the data files needed to build the installer are too big for github you will just need to download them seperatly and add them to the data folder (the icon and zip file are already there)

    data
      ├─── git_installer.exe | Git installer
      ├─── java_installer.exe | normal Java installer
      ├─── jdk_17_installer.exe | Java Development Kit installer
    