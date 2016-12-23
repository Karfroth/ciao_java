/*
 * Agenda.java
 *
 * Created on April 26, 2001, 3:47 PM
 */
// ----------------------------
// Begin of Ciao specific code.
import CiaoJava.*;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import java.util.Arrays;
import java.awt.Frame;
// End of Ciao specific code.
// ----------------------------

/**
 *
 * @author  jcorreas
 * @version 
 */
public class PhoneList extends javax.swing.JFrame {

    
    /** Creates new form PhoneList */
    public PhoneList() {
        initComponents ();
        pack ();

    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the FormEditor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel1 = new javax.swing.JPanel();
        btnSearch = new javax.swing.JButton();
        btnInsert = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        tblList = new javax.swing.JTable();
        setTitle("Telephone Directory");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        }
        );
        
        jPanel1.setLayout(new java.awt.GridLayout(1, 4));
        
        btnSearch.setToolTipText("Searches database");
          btnSearch.setText("Search...");
          btnSearch.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
                  btnSearchActionPerformed(evt);
              }
          }
          );
          jPanel1.add(btnSearch);
          
          
        btnInsert.setToolTipText("Inserts a new Contact");
          btnInsert.setLabel("Insert...");
          btnInsert.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
                  btnInsertActionPerformed(evt);
              }
          }
          );
          jPanel1.add(btnInsert);
          
          
        btnRemove.setToolTipText("Removes an existing contact");
          btnRemove.setText("Remove");
          btnRemove.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
                  btnRemoveActionPerformed(evt);
              }
          }
          );
          jPanel1.add(btnRemove);
          
          
        btnExit.setText("Exit");
          btnExit.addActionListener(new java.awt.event.ActionListener() {
              public void actionPerformed(java.awt.event.ActionEvent evt) {
                  btnExitActionPerformed(evt);
              }
          }
          );
          jPanel1.add(btnExit);
          
          
        getContentPane().add(jPanel1, java.awt.BorderLayout.SOUTH);
        
        
        tblList.setPreferredSize(new java.awt.Dimension(300, 200));
        tblList.setModel(new javax.swing.table.DefaultTableModel (
        new Object [][] {
            {null, null, null, null, null},
            {null, null, null, null, null}
        },
        new String [] {
            "Name", "Phone", "E-mail", "Address", "Position"
        }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };
            
            public boolean isCellEditable (int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblList.setMaximumSize(new java.awt.Dimension(300, 16));
        tblList.setName("grdList");
        tblList.setMinimumSize(new java.awt.Dimension(300, 16));
        
        getContentPane().add(tblList, java.awt.BorderLayout.CENTER);
        
        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dialogSize = getSize();
        setSize(new java.awt.Dimension(500, 500));
        setLocation((screenSize.width-500)/2,(screenSize.height-500)/2);
    }//GEN-END:initComponents

  /**
   * Removes an existing contact from de database.
   * The contact is referred in the current row of
   * the tblList object.
   */
  private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
    // Begin of Ciao specific code.

    // Selecting element from jTable
    int index = tblList.getSelectedRow();
    if (index >= 0) {
	Vector row = (Vector)dataModel.getDataVector().elementAt(index);
	PLTerm values[] = new PLTerm[row.size()];
        for (int i = 0 ; i < row.size() ; i++)
	    if (row.elementAt(i) == null ||
		row.elementAt(i).equals(""))
		values[i] = new PLVariable();
	    else
		values[i] = new PLAtom((String)row.elementAt(i));
        PLList lvalues = null;
        try {
            lvalues = new PLList(values);
        } catch (Exception e) {}

	PLTerm arg[] = {dbTable, lvalues, getSearchMode(NORMAL)};
	PLTerm gTerm = new PLStructure(":",
				       new PLTerm[] {
					   new PLAtom("agenda"),
					   new PLStructure("delete",arg)});

	// Launching Prolog goal.
	try {
	    PLGoal goal = new PLGoal(gTerm);
	    goal.query();
	    goal.execute();
	} catch (Exception e) {
	    System.err.println("Exception thrown while deleting element: " + e);
	}

	try {
	    dataModel.removeRow(index);
	} catch (ArrayIndexOutOfBoundsException e) {
	    // this should not occur.
	}
    }

    // End of Ciao specific code.
  }//GEN-LAST:event_btnRemoveActionPerformed

  private void btnInsertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertActionPerformed
    // Begin of Ciao specific code.

    // Showing the form
    PhoneInsert phc = new PhoneInsert(this, true);
    phc.show();
    // Actual modification on the database is made in that form, calling
    // PhoneList.insertRow(String[])
    
    // End of Ciao specific code.
  }//GEN-LAST:event_btnInsertActionPerformed

  private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
    // Begin of Ciao specific code.

    // Showing the search form
    PhoneSearch dlgSearch = new PhoneSearch(this, true);
    dlgSearch.show();
    // Phone list refreshes is made in dlgSearch, calling
    // PhoneList.refreshList(String[])
    
    // End of Ciao specific code.
  }//GEN-LAST:event_btnSearchActionPerformed

  private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
    // Begin of Ciao specific code.
    this.exit();
    // End of Ciao specific code.
  }//GEN-LAST:event_btnExitActionPerformed

    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        // Begin of Ciao specific code.
        this.exit();
        // End of Ciao specific code.
    }//GEN-LAST:event_exitForm

    /**
    * @param args the command line arguments
    */
    public static void main (String args[]) {
        PhoneList p = new PhoneList ();
        p.show2();
    }
    public void show2() {
        super.show();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnInsert;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnExit;
    private javax.swing.JTable tblList;
    // End of variables declaration//GEN-END:variables

    /* -----------------------------------------------------
     * Begin of Ciao specific code.
     * -----------------------------------------------------*/
    // Public fields.
    public static final int NORMAL = 0;   // Normal search mode.
    public static final int CASE_INS = 1; // Case insensitive search mode.
    public static final int REG_EXP = 2;  // Regular expression search mode.
    // Private fields.
    private static final PLAtom dbTable = new PLAtom("agenda_data");
    private static final PLAtom module =new PLAtom("agenda");
    private static final String[] COLNAMES = new String [] {
                "Name", "Phone", "E-mail", "Address", "Position"};
    private static final Vector colNames = new Vector(Arrays.asList(COLNAMES));
    private DefaultTableModel dataModel = null;
    private int selectedRow;

    public void show() {
        String fld[] = {new String(""), new String(""),
                        new String(""), new String(""), new String("")};
	refreshList(fld, NORMAL);
        super.show();
    }

    /**
     * Refreshes the list of contacts using the
     * filter given as argument, and the search mode.
     */
    public void refreshList(String[] filter, int searchMode){
	dataModel = new DefaultTableModel(getList(filter, searchMode),
					  colNames);
        tblList.setModel(dataModel);
    }

    /**
     * Gets a vector of data with the result of a Prolog query
     * based on the received arguments.
     */
    private Vector getList(String fieldValues[], int searchMode) {

        // Composing the Java structure that reflects the Prolog goal.
        PLTerm values[] = new PLTerm[fieldValues.length];
        for (int i = 0 ; i < values.length ; i++)
	    if (fieldValues[i].equals(""))
		values[i] = new PLVariable();
	    else
		values[i] = new PLAtom(fieldValues[i]);
        PLList lvalues = null;
        try {
            lvalues = new PLList(values);
        } catch (Exception e) {}
        PLVariable res = new PLVariable();
        PLTerm arg[] = {dbTable, lvalues, getSearchMode(searchMode), res};
 	PLTerm gTerm = new PLStructure(":",
				       new PLTerm[]
	    {module,
	     new PLStructure("search",arg)});
        
        // Launching Prolog goal.
        Vector data = new Vector();
        try {
            PLGoal goal = new PLGoal(gTerm);
            goal.query();
	    /*
	     */
	    if (goal.nextSolution() != null) {
		if (!res.getBinding().isNil()) {
		    PLTerm lres = (PLList)res.getBinding();
		    while (!lres.isNil()) {
			PLStructure s = (PLStructure)((PLList)lres).getHead();
			Vector ss = new Vector(s.getArgs().length);
			for (int i = 0 ; i <  s.getArgs().length ; i++)
			    ss.add(s.getArg(i).toString());
			data.add(ss);
			lres = ((PLList)lres).getTail();
		    }
		}
	    }
        } catch (Exception e) {
            System.err.println("Exception thrown while reading data from Prolog: " + e);
        }

        return data;
    } 
       
    /** 
     * Terminates the program.
     */
    private void exit() {

        // Composing the Java structure that reflects the Prolog goal.
 	PLTerm gTerm = new PLStructure(":",
				       new PLTerm[]
	    {module,
	     new PLAtom("exit")});
        
        // Launching Prolog goal.
        try {
            PLGoal goal = new PLGoal(gTerm);
            goal.query();
            goal.execute();
        } catch (Exception e) {
            System.err.println("Exception thrown while terminating program: " + e);
        }
    }
       
    /** 
     * Returns the Prolog constant that represents a given
     * search mode.
     */
    private PLTerm getSearchMode(int searchMode) {
        switch (searchMode) {
            case NORMAL:
                return new PLAtom("normal");
            case CASE_INS:
                return new PLAtom("case_ins");
            case REG_EXP:
                return new PLAtom("reg_exp");
        }  
        return null;
    }
        
    /**
     * Inserts a new row in the Phone database.
     * This method is used outside this frame from PhoneInsert.
     */
    public void insertRow(String[] fieldValues) {
	// Composing the Java structure that reflects the Prolog goal.
	PLTerm values[] = new PLTerm[fieldValues.length];
	for (int i = 0 ; i < fieldValues.length ; i++)
	    values[i] = new PLAtom(fieldValues[i]);
	PLList lvalues = null;
	try {
	    lvalues = new PLList(values);
	} catch (Exception e) {}

	PLTerm arg[] = {dbTable, lvalues};
 	PLTerm gTerm = new PLStructure(":",
				       new PLTerm[] {
					   module,
					   new PLStructure("insert",arg)});
        
	// Launching the Prolog goal.
	try {
	    PLGoal goal = new PLGoal(gTerm);
	    goal.query();
	    goal.execute();
	} catch (Exception e) {
	    System.err.println("Exception thrown while deleting element: " + e);
	}
	
	dataModel.addRow(fieldValues);
    }

}
