package test.frontend.impl.items.selection;

import static org.junit.Assert.*;
import helper.Position;
import helper.Size;

import java.awt.Color;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import frontend.impl.items.Line;
import frontend.impl.items.Robot;
import frontend.impl.items.handler.LineOriginHandler;
import frontend.impl.items.selection.Init;
import frontend.impl.items.selection.MyStateFactory;
import frontend.impl.items.selection.SelectionArea;
import frontend.impl.items.selection.SelectionTool;
import frontend.impl.items.selection.AbstractState;
import frontend.impl.view.Field;
import frontend.impl.view.View;
import frontend.interfaces.Item;
import frontend.interfaces.ItemHandler;

/**
 * Created by ca-za on 13.12.13.
 */
public class SelectionToolTest {

    private View view;
    private Field field;
    private SelectionTool selectionTool;
    private Position itemPosition;
    private Size itemSize;
    private Item item;
    private ItemHandler handler;
    private Item robot;
    private Position robotPosition;
    private Size robotSize;

    @Before
    public void setUp() {
        this.view = new View(new Size(600, 300), new Size(10, 10), Color.BLACK);
        this.field = new Field(this.view, new Size(600, 300), new Size(10, 10));
        this.selectionTool = new SelectionTool(this.field);

        this.itemPosition = new Position(30, 30);
        this.itemSize = new Size(0, 50);
        this.item = new Line(this.itemPosition, this.field);
        this.item.setSize(this.itemSize);
        this.field.addItem(this.item);
        
        this.robotPosition = new Position(60, 50);
        this.robotSize = new Size(10, 10);
        this.robot = new Robot(this.robotPosition, this.robotSize, this.field);
        this.field.addItem(this.robot);
        
        this.handler = new LineOriginHandler(this.item, this.item.getPosition(),this.field);
    }

    @Test
    public void testGetStateFactory() {
        assertTrue(this.selectionTool.getStateFactory() instanceof MyStateFactory);
    }

    @Test
    public void testGetItemByPosition() {
        assertEquals(this.item, this.selectionTool.getItemByPosition(this.itemPosition));
        assertNull(this.selectionTool.getItemByPosition(new Position(10, 10)));
    }
    
    // Test selectionArea Functions
    @Test
    public void testSelectionArea() {
    	//set State to normal Init
    	this.selectionTool.setToolState(new Init(this.selectionTool));
    	
    	//mouseDown on Empty Area should create new SelectionArea Item
    	this.selectionTool.mouseDown(new Position(10, 10));
    	
    	//check if SelectionArea has been created
    	Item selectionArea = null;
    	for(Item i : this.field.getItems()) {
    		if(i instanceof SelectionArea) selectionArea = i;
    	}
    	
    	assertTrue(selectionArea != null);
    	
    	//check if after MouseDrag SelectionArea and Selection has been adjusted
    	this.selectionTool.mouseDrag(new Position(80, 100));
    	assertEquals(new Size(70, 90), selectionArea.getSize());
    	assertTrue(this.selectionTool.getSelection().contains(this.item));
    	assertTrue(this.selectionTool.getSelection().contains(this.robot));

    	//check if after MouseDrag SelectionArea and Selection has been adjusted
    	this.selectionTool.mouseDrag(new Position(40, 100));
    	assertTrue(this.selectionTool.getSelection().contains(this.item));
    	assertFalse(this.selectionTool.getSelection().contains(this.robot));
    	
    	//check if after MouseUp SelectionArea has been removed && Item is still selected
    	this.selectionTool.mouseUp(new Position(40, 100));
    	selectionArea = null;
    	for(Item i : this.field.getItems()) {
    		if(i instanceof SelectionArea) selectionArea = i;
    	}
    	
    	assertTrue(selectionArea == null);
    	assertTrue(this.selectionTool.getSelection().contains(this.item));
    	
    	//check if clearSelection
    	this.selectionTool.clearSelection();
    	assertTrue(this.selectionTool.getSelection().isEmpty());
    }
    
    //Test set, get and clearSelection
    @Test
    public void testSelection() {
    	this.selectionTool.doAddToSelection(this.item);
    	assertTrue(this.selectionTool.getSelection().contains(this.item));
    	this.selectionTool.clearSelection();
    	assertTrue(this.selectionTool.getSelection().isEmpty());
    }

    @Test
    public void testDoAddToSelection() {
    	this.selectionTool.doAddToSelection(item);

    }
    
    // Test set and getCurrentHandle
    @Test
    public void testCurrentHandle() {
    	this.selectionTool.setCurrentHandle(this.handler);
    	assertEquals(this.handler, this.selectionTool.getCurrentHandle());
    }

    @Test
    public void testGetItemHandlerByPosition() {
    	this.selectionTool.doAddToSelection(this.item);
    	assertEquals(this.item.getItemHandler().get(0), 
    			this.selectionTool.getItemHandlerByPosition(this.itemPosition));
    	assertNull(this.selectionTool.getItemHandlerByPosition(new Position(10, 10)));
    	this.selectionTool.clearSelection();
    }

    @Test
    public void testIsOnItemHandle() {
    	this.selectionTool.doAddToSelection(this.item);
    	assertTrue(this.selectionTool.isOnItemHandle(this.itemPosition));
    	this.selectionTool.clearSelection();
    }

    @Test
    public void testIsOnUnselectedItem() {
    	assertTrue(this.selectionTool.isOnUnselectedItem(new Position(30, 40)));
    	this.selectionTool.doAddToSelection(this.item);
    	assertFalse(this.selectionTool.isOnUnselectedItem(new Position(30, 40)));
    	this.selectionTool.clearSelection();
    }

    @Test
    public void testIsOnSelectedItem() {
    	this.selectionTool.doAddToSelection(this.item);
    	assertTrue(this.selectionTool.isOnSelectedItem(new Position(30, 40)));
    	this.selectionTool.clearSelection();
    }

    @Test
    public void testIsOnEmptyArea() {
    	assertTrue(this.selectionTool.isOnEmptyArea(new Position(10, 10)));
    	assertFalse(this.selectionTool.isOnEmptyArea(this.itemPosition));
    }

    @Test
    public void testGetPreviousMouseDragPosition() {
    	Position testP = new Position(70, 70);
    	this.selectionTool.mouseDrag(testP);
    	assertEquals(testP, this.selectionTool.getPreviousMouseDragPosition());
    }

    //Test doMove and endMoveSelectedItems
    @Test
    public void testDoMoveSelectedItems() {
    	//reset State
    	this.selectionTool.setToolState(new Init(this.selectionTool));
    	
    	//MouseDown on item creates new Moving State
    	this.selectionTool.mouseDown(this.itemPosition);
    	
    	//should call doMoveSelectedItem and move item
    	Position testP = new Position(60, 30);
    	this.selectionTool.mouseDrag(testP);
    	assertEquals(testP, this.item.getPosition());
    	
    	//should call endMoveSelectedItem and reset item to itemPosition
    	this.selectionTool.mouseUp(testP);
    	assertEquals(this.itemPosition, this.item.getPosition());
    	
    	//should call doEndMoveSelectedItem and not reseting Item
    	this.selectionTool.mouseDown(this.itemPosition);
    	testP = new Position(10, 10);
    	this.selectionTool.mouseUp(testP);
    	assertEquals(testP, this.item.getPosition());
    	
    	//reset Item
    	this.item.setPosition(this.itemPosition);
    	
    	//reset Selection
    	this.selectionTool.clearSelection();
    }
    
    //Test Drag Hanlder
    @Test
    public void testDragHandler() {
    	//select item
    	this.selectionTool.mouseDown(new Position(30, 40));
    	this.selectionTool.mouseUp(new Position(30,40));
    	
    	//mouseDown on origin Handler
    	this.selectionTool.mouseDown(new Position(30, 30));
    	
    	//mouse drag should resize the item
    	this.selectionTool.mouseDrag(new Position(30, 20));
    	assertEquals(this.item.getSize(), new Size(0, 60));
    	
    	//mouse up should resize the item again
    	this.selectionTool.mouseUp(new Position(30, 30));
    	assertEquals(this.item.getSize(), new Size(0, 50));
    }
    
    /**
     * test if mouse down on empty are removes all items from selection
     */
    @Test
    public void testEmptyArea() {
    	//select item
    	this.selectionTool.mouseDown(new Position(30, 40));
    	this.selectionTool.mouseUp(new Position(30,40));
    	
    	assertTrue(this.selectionTool.getSelection().contains(this.item));
    	
    	//mouse down on empty area should remove item from selection
    	this.selectionTool.mouseDown(new Position(10, 10));
    	assertFalse(this.selectionTool.getSelection().contains(this.item));
    }
}
