<details>
  <summary>Database tables (db2)</summary>
  
  ![](https://i.imgur.com/vwoYHE3.png)
  ![](https://i.imgur.com/yY2V8yL.png)
  ![](https://i.imgur.com/qMpQaaV.png)
</details>

# Logging in and showcase

![](https://i.imgur.com/5YRXdnZ.gif)

User can see his own records (private and public) + others' public.  
Admin can see all records.

Login form uses j_security_check. 
<details>
  <summary>Glassfish security realm config</summary>
  
  ![](https://i.imgur.com/2W7T4PP.png)
</details>

# User Deletion

![](https://i.imgur.com/kONl89l.gif)

# Search

![](https://i.imgur.com/3SB6k0T.gif)

Searches in all columns.

# Pagination

![](https://i.imgur.com/fgEt6kh.gif)

# User tries to access '/users' page (admin only feature)

![](https://i.imgur.com/xORQSTr.gif)

# Add phone

![](https://i.imgur.com/atAwCd0.gif)

[intl-tel-input](https://github.com/jackocnr/intl-tel-input) was used for phone indexes.

Actually a phone record should be attached to an existing user's email (still need to implement email existence check on server), but I generated these records so they have random emails.

# Errors during phone add

![](https://i.imgur.com/T2dsjUZ.gif)

'Fullname' and 'Address' - not null && length < 255  
'Email' and 'Phone' - using regular expressions on both server and client side.

# Phone edit

![](https://i.imgur.com/UwjA7kZ.gif)

# Phone delete

![](https://i.imgur.com/SOL9745.gif)
