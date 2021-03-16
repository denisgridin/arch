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


LAB5
savePresentation
entityName="Presentation"
fill_color="black"
font_family="Roboto"

LAB5
getPresentationById
id=fa7fd31e-1567-4ec8-b3fc-2b90a4495650
entityName="Presentation edited"
fill_color="white"
font_family="Comic Sans"

LAB5
addPresentationSlide
presentation_id=fa7fd31e-1567-4ec8-b3fc-2b90a4495650
entityName="Slide"
slide_index=1

LAB5
getPresentationSlides
presentation_id=fa7fd31e-1567-4ec8-b3fc-2b90a4495650

LAB5
getSlideById
id=F173EB04-D1E7-4A27-BDE9-737AB61074A0

LAB5
updateSlide
id=F173EB04-D1E7-4A27-BDE9-737AB61074A0
slide_index=2
entityName="edited slide"

LAB5
addPresentationComment
presentation_id=707C578C-BA2A-4812-8794-FCFA1F004EA8
text="comment text"
role=editor


LAB5
getPresentationComments
presentation_id=707C578C-BA2A-4812-8794-FCFA1F004EA8


LAB5
getPresentationComments
presentation_id=707C578C-BA2A-4812-8794-FCFA1F004EA8
id=487e8b41-d36f-44b0-b3c4-ae2373481427
text="edited text"
role=guest

LAB5
updateComment
presentation_id=707C578C-BA2A-4812-8794-FCFA1F004EA8
id=487e8b41-d36f-44b0-b3c4-ae2373481427
text="edited text"
role=guest


LAB5
updateComment
presentation_id=707C578C-BA2A-4812-8794-FCFA1F004EA8
id=487E8B41-D36F-44B0-B3C4-AE2373481427
text="edited text"
role="guest"

LAB5
addSlideShape
slide_id=78400329-F23A-4736-991B-F6832EE32D96
element_type=shape
text="inner text"
figure=rectangle
border_style=dashed
border_color=yellow
border_radius=1px
border_width=10px
box_shadow=none
opacity=100
fill_color=black
height=100
rotation=10
width=100
x=10
y=10
entityName="element name"



LAB5
addSlideContent
slide_id=78400329-F23A-4736-991B-F6832EE32D96
element_type=content
text="inner text"
font_case=normal
font_family="Roboto"
font_size="10px"
letter_spacing="2px"
line_spacing="3px"
height=100
rotation=10
width=100
x=10
y=10
entityName="element name"

LAB5
updateSlideElement
id=710e2a00-9385-4917-85e7-cbf000d3548c
slide_id=78400329-F23A-4736-991B-F6832EE32D96
element_type=content
text="inner text"
font_case=normal
font_family="Roboto"
font_size="10px"
letter_spacing="2px"
line_spacing="3px"
height=100
rotation=10
width=100
x=10
y=10
entityName="element name edited"



LAB5
updateSlideElement
id=72E95588-D954-4DAA-B7E3-A78758D5C3C5
slide_id=78400329-F23A-4736-991B-F6832EE32D96
element_type=shape
text="inner text"
figure=rectangle
border_style=dashed
border_color=yellow
border_radius=1px
border_width=10px
box_shadow=none
opacity=100
fill_color=black
height=100
rotation=10
width=100
x=10
y=10
entityName="element name"

LAB5
getSlideElements
id=78400329-F23A-4736-991B-F6832EE32D96

LAB5
generateAssessments
id=FC385AFF-88D3-436A-8AAC-14955E5F3078

LAB5
updateAssessment
id=1667E451-AB17-44CC-A4A8-FA89F0335C3D
mark=good
role=guest

LAB5
getSummaryCriteria