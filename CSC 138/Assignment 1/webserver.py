# Import socket library
from socket import *

# Import sys package if you want to terminate the program
import sys
def create_server_socket(port):
    # Prepare a sever socket
    # Fill in start
    serverSocket = socket(AF_INET,SOCK_STREAM)
    serverSocket.bind(('',port))
    serverSocket.listen(1)
    # Fill in end
    print(f"The server is ready to receive on port: {port}")
    return serverSocket

def handle_request(connectionSocket):
    try:
        # Receive the HTTP request
        message = connectionSocket.recv(2048).decode()
        # Prepare HTTP response header s    
        # Fill in start
        header = "HTTP/1.1 200 OK\nContent Type: text/html\n\n"
        # Fill in end

        # Get the requested file from the message
        filename = message.split()[1][1:]

        # Open the requested file and get the HTML body content
        # Fill in start
        f = open(filename, 'r')
        body_contents =f.read()
        # Fill in end

        # Send response message
        # Fill in start
        connectionSocket.send(header.encode())
        connectionSocket.send(body_contents.encode())
        # Fill in end

        # Close the socket
        # Fill in start
        connectionSocket.close()
        # Fill in end

        # Terminate the program after sending the corresponding data
        # Comment it out if you want the server to be always ON
        # sys.exit()


    except IOError:
        # Prepare 404 Not Found HTTP header
        # Fill in start
        header = "HTTP/1.1 404 Not Found\nContent Type: text/html\n"
        # Fill in end

        # Prepare the HTML body content of 404 Not Found page
        # Fill in start
        not_found_contents = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Welcome to CSC 138 Computer Networking</title>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    text-align: center;
                    margin: 50px;
                }  
            </style>
        </head>
        <body>
            <h1>404 Not Found</h1>
        </body>
        </html>"""
        # Fill in end

        # Send response message
        # Fill in start
        connectionSocket.send(header.encode())
        connectionSocket.send(not_found_contents.encode())
        # Fill in end

        # Close socket
        # Fill in start
        connectionSocket.close()
        # Fill in end


if __name__ == "__main__":
    port = 12000
    serverSocket = create_server_socket(port)
    while True:
        connectionSocket, addr = serverSocket.accept()
        handle_request(connectionSocket)
