# Get报文格式

**记得每一行末尾都有 \r\n的换行符**

![image-20211015201122919](.\imgs\image-20211015201122919.png)

# POST报文格式

**Content-Type : application/json格式**

![image-20211015205335512](.\imgs\image-20211015205335512.png)

Content-Type ,Content-Length

Cookie: Cookie_1=value; Cookie_2=value; Cookie_3=value; Cookie_4=value



# Axios跨域问题

**响应添加这个即可**

```java
client.getOutputStream().write("access-control-allow-origin: *".getBytes(StandardCharsets.UTF_8));
client.getOutputStream().write(FinalString.CRLF.getString().getBytes(StandardCharsets.UTF_8));
```

