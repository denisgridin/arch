package ru.sfedu.Arch.lab5.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.tool.schema.internal.StandardTableExporter;
import ru.sfedu.Arch.Constants;
import ru.sfedu.Arch.Enums;
import ru.sfedu.Arch.Result;
import ru.sfedu.Arch.Runner;
import ru.sfedu.Arch.lab5.model.*;
import ru.sfedu.Arch.utils.Messages;

import javax.swing.text.html.Option;
import java.sql.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class CLIProvider {
    public CLIProvider () {}

    public static Logger log = LogManager.getLogger(CLIProvider.class);

    public static Result run (String method, HashMap args) {
        try {
            Enums.METHODS_LAB_5 function = Enums.METHODS_LAB_5.valueOf(method);
            ApiProvider api = new ApiProvider();

            switch (function) {
                case savePresentation: {
                    Presentation presentation = new Presentation();
                    presentation.setName((String) args.get(Constants.FIELD_NAME));
                    presentation.setFontFamily((String) args.get(Constants.FIELD_FONT_FAMILY));
                    presentation.setFillColor((String) args.get(Constants.FIELD_FILL_COLOR));
                    return api.savePresentation(presentation);
                }
                case getPresentationById: {
                    UUID id = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    return api.getPresentationById(id);
                }
                case updatePresentation: {
                    Presentation presentation = new Presentation();
                    presentation.setName((String) args.get(Constants.FIELD_NAME));
                    presentation.setFontFamily((String) args.get(Constants.FIELD_FONT_FAMILY));
                    presentation.setFillColor((String) args.get(Constants.FIELD_FILL_COLOR));
                    presentation.setId(UUID.fromString((String) args.get(Constants.FIELD_ID)));
                    return api.updatePresentation(presentation);
                }
                case addPresentationSlide: {
                    Slide slide = new Slide();
                    slide.setIndex(Integer.parseInt((String) args.get(Constants.FIELD_INDEX)));
                    slide.setName((String) args.get(Constants.FIELD_NAME));
                    UUID presentationId = UUID.fromString((String) args.get(Constants.FIELD_PRESENTATION_ID));
                    return api.addPresentationSlide(slide, presentationId);
                }
                case getPresentationSlides: {
                    UUID presentationId = UUID.fromString((String) args.get(Constants.FIELD_PRESENTATION_ID));
                    return api.getPresentationSlides(presentationId);
                }
                case getSlideById: {
                    UUID slideId = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    return api.getSlideById(slideId);
                }
                case updateSlide: {
                    UUID slideId = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    Result result = api.getSlideById(slideId);
                    if (result.getStatus() == Enums.STATUS.success) {
                        Optional<Slide> optionalSlide = (Optional<Slide>) result.getReturnValue();
                        Slide slide = optionalSlide.get();
                        slide.setName((String) args.getOrDefault(Constants.FIELD_NAME, slide.getName()));
                        slide.setIndex(Integer.parseInt(String.valueOf(args.getOrDefault(Constants.FIELD_INDEX, slide.getIndex()))));
                        return api.updateSlide(slide);
                    } else {
                        return result;
                    }
                }
                case addPresentationComment: {
                    Comment comment = new Comment();
                    comment.setRole(Enums.Role.valueOf((String) args.get(Constants.FIELD_ROLE)));
                    comment.setText((String) args.get(Constants.FIELD_TEXT));
                    comment.setDatetime(String.valueOf(new Date(System.currentTimeMillis())));

                    UUID presentationId = UUID.fromString((String) args.get(Constants.FIELD_PRESENTATION_ID));
                    return api.addPresentationComment(comment, presentationId);
                }
                case getPresentationComments: {
                    UUID id = UUID.fromString((String) args.get(Constants.FIELD_PRESENTATION_ID));
                    return api.getPresentationComments(id);
                }
                case updateComment: {
                    UUID id = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    Result result = api.getCommentById(id);
                    if (result.getStatus() == Enums.STATUS.success) {
                        Optional<Comment> optionalComment = (Optional<Comment>) result.getReturnValue();
                        Comment comment = optionalComment.get();
                        comment.setRole(Enums.Role.valueOf((String.valueOf(args.getOrDefault(Constants.FIELD_ROLE, comment.getRole())))));
                        comment.setText((String) args.getOrDefault(Constants.FIELD_TEXT, comment.getText()));
                        return api.updateComment(comment);
                    } else {
                        return result;
                    }
                }
                case getCommentById: {
                    UUID id = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    return api.getCommentById(id);
                }
                case addSlideShape: {
                    Shape shape = new Shape();
                    Enums.Figure figure = Enums.Figure.valueOf((String) args.get(Constants.FIELD_FIGURE));
                    String text = (String) args.get(Constants.FIELD_TEXT);
                    Enums.ElementType elementType = Enums.ElementType.valueOf((String) args.get(Constants.FIELD_ELEMENT_TYPE));
                    String name = (String) args.get(Constants.FIELD_NAME);

                    Style style = new Style();
                    style.setBorderStyle(Enums.BorderStyle.valueOf((String) args.get(Constants.FIELD_BORDER_STYLE)));
                    style.setBorderColor((String) args.get(Constants.FIELD_BORDER_COLOR));
                    style.setBorderRadius((String) args.get(Constants.FIELD_BORDER_RADIUS));
                    style.setBorderWidth((String) args.get(Constants.FIELD_BORDER_WIDTH));
                    style.setBoxShadow((String) args.get(Constants.FIELD_BOX_SHADOW));
                    style.setOpacity((String) args.get(Constants.FIELD_OPACITY));
                    style.setFillColor((String) args.get(Constants.FIELD_FILL_COLOR));

                    Layout layout = new Layout();
                    layout.setHeight(Integer.parseInt((String) args.get(Constants.FIELD_HEIGHT)));
                    layout.setRotation(Integer.parseInt((String) args.get(Constants.FIELD_ROTATION)));
                    layout.setWidth(Integer.parseInt((String) args.get(Constants.FIELD_WIDTH)));
                    layout.setX(Integer.parseInt((String) args.get(Constants.FIELD_X)));
                    layout.setY(Integer.parseInt((String) args.get(Constants.FIELD_Y)));

                    shape.setFigure(figure);
                    shape.setText(text);
                    shape.setStyle(style);
                    shape.setElementType(elementType);

                    layout.setElement(shape);
                    style.setShape(shape);

                    shape.setLayout(layout);
                    shape.setStyle(style);
                    shape.setName(name);

                    UUID slideId = UUID.fromString((String) args.get(Constants.FIELD_SLIDE_ID));
                    return api.addSlideShape(shape, slideId);
                }
                case addSlideContent: {
                    Content content = new Content();
                    String text = (String) args.get(Constants.FIELD_TEXT);
                    Enums.ElementType elementType = Enums.ElementType.valueOf((String) args.get(Constants.FIELD_ELEMENT_TYPE));
                    String name = (String) args.get(Constants.FIELD_NAME);

                    Layout layout = new Layout();
                    layout.setHeight(Integer.parseInt((String) args.get(Constants.FIELD_HEIGHT)));
                    layout.setRotation(Integer.parseInt((String) args.get(Constants.FIELD_ROTATION)));
                    layout.setWidth(Integer.parseInt((String) args.get(Constants.FIELD_WIDTH)));
                    layout.setX(Integer.parseInt((String) args.get(Constants.FIELD_X)));
                    layout.setY(Integer.parseInt((String) args.get(Constants.FIELD_Y)));

                    content.setText(text);
                    content.setElementType(elementType);

                    layout.setElement(content);

                    content.setLayout(layout);
                    content.setName(name);

                    Font font = new Font();
                    font.setFontCase(Enums.FontCase.valueOf((String) args.get(Constants.FIELD_FONT_CASE)));
                    font.setFontFamily((String) args.get(Constants.FIELD_FONT_FAMILY));
                    font.setFontSize((String) args.get(Constants.FIELD_FONT_SIZE));
                    font.setLetterSpacing((String) args.get(Constants.FIELD_LETTER_SPACING));
                    font.setLineSpacing((String) args.get(Constants.FIELD_LINE_SPACING));

                    font.setContent(content);
                    content.setFont(font);

                    UUID slideId = UUID.fromString((String) args.get(Constants.FIELD_SLIDE_ID));
                    return api.addSlideContent(content, slideId);
                }
                case updateSlideElement: {
                    UUID id = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    UUID slideId = UUID.fromString((String) args.get(Constants.FIELD_SLIDE_ID));
                    Result result = api.getSlideElementById(id);

                    if (result.getStatus() == Enums.STATUS.success) {
                        Optional<Element> optionalElement = (Optional<Element>) result.getReturnValue();
                        Element element = optionalElement.get();
                        Enums.ElementType elementType = Enums.ElementType.valueOf((String) args.get(Constants.FIELD_ELEMENT_TYPE));
                        String name = (String) args.getOrDefault(Constants.FIELD_NAME, element.getName());

                        Layout layout = element.getLayout();
                        layout.setHeight(Integer.parseInt((String) args.getOrDefault(Constants.FIELD_HEIGHT, layout.getHeight())));
                        layout.setRotation(Integer.parseInt((String) args.getOrDefault(Constants.FIELD_ROTATION, layout.getRotation())));
                        layout.setWidth(Integer.parseInt((String) args.getOrDefault(Constants.FIELD_WIDTH, layout.getWidth())));
                        layout.setX(Integer.parseInt((String) args.getOrDefault(Constants.FIELD_X, layout.getX())));
                        layout.setY(Integer.parseInt((String) args.getOrDefault(Constants.FIELD_Y, layout.getY())));

                        element.setLayout(layout);
                        element.setName(name);
                        if (element.getElementType() == Enums.ElementType.shape) {
                            Shape shape = (Shape) element;
                            Style style = shape.getStyle();
                            String text = (String) args.getOrDefault(Constants.FIELD_TEXT, shape.getText());
                            Enums.Figure figure = Enums.Figure.valueOf((String) args.getOrDefault(Constants.FIELD_FIGURE, shape.getFigure()));
                            style.setBorderStyle(Enums.BorderStyle.valueOf((String) args.get(Constants.FIELD_BORDER_STYLE)));
                            style.setBorderColor((String) args.getOrDefault(Constants.FIELD_BORDER_COLOR, style.getBorderColor()));
                            style.setBorderRadius((String) args.getOrDefault(Constants.FIELD_BORDER_RADIUS, style.getBorderRadius()));
                            style.setBorderWidth((String) args.getOrDefault(Constants.FIELD_BORDER_WIDTH, style.getBorderWidth()));
                            style.setBoxShadow((String) args.getOrDefault(Constants.FIELD_BOX_SHADOW, style.getBoxShadow()));
                            style.setOpacity((String) args.getOrDefault(Constants.FIELD_OPACITY, style.getOpacity()));
                            style.setFillColor((String) args.getOrDefault(Constants.FIELD_FILL_COLOR, style.getFillColor()));
                            shape.setStyle(style);
                            shape.setText(text);
                            shape.setFigure(figure);
                            style.setShape(shape);

                            return api.updateSlideElement(shape, slideId);
                        } else {
                            Content content = (Content) element;
                            String text = (String) args.getOrDefault(Constants.FIELD_TEXT, content.getText());
                            Font font = content.getFont();
                            font.setFontCase(Enums.FontCase.valueOf((String) args.getOrDefault(Constants.FIELD_FONT_CASE, font.getFontCase())));
                            font.setFontFamily((String) args.getOrDefault(Constants.FIELD_FONT_FAMILY, font.getFontFamily()));
                            font.setFontSize((String) args.getOrDefault(Constants.FIELD_FONT_SIZE, font.getFontSize()));
                            font.setLetterSpacing((String) args.getOrDefault(Constants.FIELD_LETTER_SPACING, font.getLetterSpacing()));
                            font.setLineSpacing((String) args.getOrDefault(Constants.FIELD_LINE_SPACING, font.getLineSpacing()));

                            content.setText(text);
                            font.setContent(content);
                            content.setFont(font);
                            return api.updateSlideElement(content, slideId);
                        }

                    }
                }
                case getSlideElementById: {
                    UUID id = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    return api.getSlideElementById(id);
                }
                case getSlideElements: {
                    UUID id = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    return api.getSlideElements(id);
                }
                case deleteSlideElement: {
                    UUID id = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    return api.deleteSlideElement(id);
                }
                case generateAssessments: {
                    UUID id = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    return api.generateAssessments(id);
                }
                case getAssessmentById: {
                    UUID id = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    return api.getAssessmentById(id);
                }
                case updateAssessment: {
                    UUID id = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    Result result = api.getAssessmentById(id);

                    if (result.getStatus() == Enums.STATUS.success) {
                        Optional<Assessment> optionalAssessment = (Optional<Assessment>) result.getReturnValue();
                        Assessment assessment = optionalAssessment.get();

                        Enums.Mark mark = Enums.Mark.valueOf(String.valueOf(args.getOrDefault(Constants.FIELD_MARK, assessment.getMark())));
                        Enums.Role role = Enums.Role.valueOf(String.valueOf(args.getOrDefault(Constants.FIELD_ROLE, assessment.getRole())));
                        assessment.setMark(mark);
                        assessment.setRole(role);
                        return api.updateAssessment(assessment);

                    } else {
                        return result;
                    }
                }
                case deleteAssessment: {
                    UUID id = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    return api.deleteAssessment(id);
                }
                case deleteComment: {
                    UUID id = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    return api.deleteComment(id);
                }
                case deleteSlide: {
                    UUID id = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    return api.deleteSlide(id);
                }
                case deletePresentationById: {
                    UUID id = UUID.fromString((String) args.get(Constants.FIELD_ID));
                    return api.deletePresentationById(id);
                }
                case getSummaryCriteria: {
                    return api.getSummaryCriteria();
                }
                case getSummaryHQL: {
                    return api.getSummaryNative();
                }
                default: {
                    return new Result(Enums.STATUS.error, Messages.ERROR_METHOD_RUN);
                }
            }


        } catch (Exception error) {
            log.error(error);
            log.error(Messages.ERROR_METHOD_RUN);
            return new Result(Enums.STATUS.error, Messages.ERROR_METHOD_RUN);
        }
    }
}
