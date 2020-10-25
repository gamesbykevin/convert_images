## Convert Images
### Read all images in a given directory and convert them to binary and grey scale ".png" images
#### This program will scan all directory and sub directories for images, supported "png", "jpg", "gif"

1) maven clean

2) maven package

3) place jar in the same folder and execute run.bat

```
java -jar convert_images-1.0-SNAPSHOT
```

4) or modify run.bat to include multiple directories to be converted
```
java -jar convert.jar "C:\Users\kevin\image_folder" "C:\Users\kevin\other_folder"
```
