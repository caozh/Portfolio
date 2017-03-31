README

<Program Name>
  slidingReceiver.repy
  slidingSender.repy

<Author>
  Cong Qi	congqi@iupui.edu
  Zhihao Cao 	caozh@iupui.edu
  Rubin Xiao	rxiao@iupui.edu

<Purpose>
  This code is an implementation of Sliding Window Protocol base on Stop-and-Wait
 Assignment can be found at:
  https://seattle.poly.edu/wiki/EducationalAssignments/SlidingWindow

  slidingSender.repy reads data from a file and sends it to a server program in a reliable way. The server program (slidingReceiver.repy) must be running before slidingSender.repy starts.

<Dependencies>
  To run these codes, you must have installed Seattle.
  Create at least two vessels(nodes) in your Seattle Clearinghouse Vessel page.
  Seattle introduction guid:
  https://seattle.poly.edu/wiki/EducationalAssignments/TakeHome

  For testing purpose, we only use LAN vessels as our virtual end system.
  LAN helps reducing unknown error due to many reasons.
  

<Instructions>
To use this program:
    First, run slidingReceiver.repy on a node. You must specify a port that will be used  for this server to receive and send packet.
    Example format: on %1 run slidingReceiver.repy (Port number)
    Make sure you write down the ip address of this node because you are going to need it for slidingSender.repy

    Next, you need to upload a data file to a client node for later use, here we use UDPFile.file as the default file.Type in command: on %2 upload UDPFile.file
    
    Next, run slidingSender.repy on the node that has the UDPFile.file 
You must enter the ip address of server and specify a port for this node to receive and send packets.
    Example command: on %2 run slidingSender.repy (server IP) (Port number)

Type in command ‘show log?to see the log file of the SlidingWindow activation.
Example command:
on %1 show log (This will show the log file of receiver(sender) )
on %2 show log (This will show the log file of sender(client) )

**Algotithm**

<img src="images\b.png" >
<img src="images\c.png" >





