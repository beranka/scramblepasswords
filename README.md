# Scramble Passwords
![image](https://img.shields.io/badge/Kotlin-6c0974?&style=for-the-badge&logo=kotlin&logoColor=white) ![image](https://img.shields.io/badge/Android-a4ca39?style=for-the-badge&logo=android&logoColor=white) ![image](https://img.shields.io/badge/Contains-17%20liters%20of%20tea-red?style=for-the-badge&color=9f0d52&labelColor=4a0626)

An Android application for displaying passwords for scramble PDFs at official WCA Competitions.

If I ever make this public (as it's probably very much 🍝), feel free to use it at your competitions, I would be happy, although it's probably only Emma problem.

If you're not a speedcuber and don't know anything about the WCA, WCA Competitions and Delegates, take a look at [WCA website](https://www.worldcubeassociation.org/). Feel free to email me (my email address can be found on the website since I am a Delegate), if there is something about competitive speedcubing, that you would like to know, I would love to answer.

## The problem
WCA Delegates have generally 2 options how to handle scrambles. That is either printing them or displaying them on a tablet or computer screen.

When using display devices, one option is keeping them on a separate USB drive and only putting there the currently used set, but that's problematic with tablets. In Poland (and now also Czechia), the Delegate would usually upload password protected PDFs to the device beforehand all at once and then when is time to use each set, they would tell the scramblers the respective password.

However, at least for me this is for some reason impossible and nobody ever understands me the first time I say it. And that is seriously frustrating and if I were to delegate a few more competition like this, I would be very, very unhappy. So that is why I made this.

I also did this as a final project for [CS50x](https://cs50.harvard.edu/x/2023/), because I literally finished all the problem sets in 2 weeks and then I proceeded to procrastinate for a month 🙃.

## Usage
1. Click on "Upload passwords"
2. Put in the competition name and sorted scramble passwords. It ignores all the lines that don't have exactly one ":", so if you for some reason don't care about the "sorted" part, feel free to paste there the whole original file that TNoodle generates, it should be able to handle it.
3. Click on "Submit".
4. When at the competition, click on "Display current passwords", find the right scramble set and show the screen to your scramblers. As only that one password is visible, it's okay to do that.
5. Be happy, because you don't have to repeat the same sequence of random characters 7 times!

## Technologies
I built this in Kotlin in Android Studio, it uses View Binding, SharedPreferences, Material 3 and supports dynamic colors (from Android 12). I made it's launcher icon in Inkscape.
