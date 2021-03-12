java -jar -DhibernateFile=./configs/lab1.hibernate.cfg.xml  Arch-1.0.jar LAB1 getAllTables

LAB2
update
id=19
isCheck=true
descriptionData="Description of bean"
entityName="Entity name"
specializationLevel=Middle
specializationYears=4
specializationName=Backend

LAB2
save
isCheck=true
descriptionData="Description of bean description"
entityName="Denis Gridin"
specializationLevel=Middle
specializationYears=12
specializationName=DevOps

LAB2
delete
id=20

LAB2 getById id=26


LAB3
saveComment
strategy=MAPPED_SUPERCLASS
role=editor
text="Comment text"

LAB3
getCommentById
strategy=MAPPED_SUPERCLASS
id=aa50e2f7-e8de-46bc-b4f8-c0f2ead0af76

LAB3
updateComment
id=aa50e2f7-e8de-46bc-b4f8-c0f2ead0af76
presentationId=aa50e2f7-e8de-46bc-b4f8-c0f2ead0af71
strategy=MAPPED_SUPERCLASS
role=guest
text="Edited Comment text"

LAB3
getCommentById
id=aa50e2f7-e8de-46bc-b4f8-c0f2ead0af76
strategy=MAPPED_SUPERCLASS
role=guest
text="Edited Comment text"