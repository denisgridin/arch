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


LAB3
getAssessmentById
strategy=TABLE_PER_CLASS
role=guest
mark=good
presentationId=b01bd9ac-84f0-11eb-8dcd-0242ac130003
id=59bced03-6d37-4a27-9313-cbea4567d1b8



LAB4
savePresentation
strategy=SET
entityName="New presentation"
fill_color="yellow"
font_family="Roboto"


LAB4
addPresentationSlide
strategy=SET
presentationId=AEBC80E3-319A-480D-A6AC-011357F93500
entityName="New presentation slide"
index=1

LAB4
getSlideById
strategy=SET
id=5144C11D-B6AE-40A4-84BF-92D3D30043D1
presentation_id=AEBC80E3-319A-480D-A6AC-011357F93500
entityName="New presentation slide (map strategy) updated"
slide_index=2