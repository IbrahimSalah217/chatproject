<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

   
    <xsl:template match="/">
        <html>
            <head>
                <title>Chat Message</title>
                <style>
                    
                  
                    
                    .container {
                    border: 2px solid #dedede;
                    background-color: #f1f1f1;
                    border-radius: 5px;
                    padding: 20px;
                    margin: 10px 0;
                    }
                    .darker {
                    border-color: #ccc;
                    background-color: #ddd;
                    }
                    .container::after {
                    content: "";
                    clear: both;
                    display: table;
                    }
                    .time-right {
                    float: right;
                    color: #aaa;
                    }

                    .time-left {
                    float: left;
                    color: #999;
                    }
                </style>
            </head> 
            <body>
                <xsl:for-each select="chatSession/Messages/Msg">
                    <div class="container darker">
                        <h style="font-size: 200%">
                            
                            <b>
                                <xsl:value-of select="from"/>
                            </b>
                            
                        </h>
                        <p  style="background-color: #61d85b;color: white;font-size: 150%;font-family: verdana">
                        
                            <xsl:value-of select="content"/>
                        
                        </p>
                        <span class="time-left">
                            <xsl:value-of select="time"/>
                        </span>
                    </div>
                </xsl:for-each>
                
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>