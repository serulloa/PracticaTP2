package tp.pr6.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import tp.pr6.control.Controller;
import tp.pr6.model.Event;
import tp.pr6.model.RoadMap;
import tp.pr6.model.SimulatorError;
import tp.pr6.model.TrafficSimulator;
import tp.pr6.model.TrafficSimulator.TrafficSimulatorObserver;

public class MainWindow extends JFrame implements TrafficSimulatorObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Controller ctrl; // la vista usa el controlador
	private RoadMap map; // para los métodos update de Observer
	private int time; // para los métodos update de Observer
	private List<Event> events; // para los métodos update de Observer 
	private OutputStream reportsOutputStream;
	
	private JPanel mainPanel; 
	private JPanel contentPanel_1; // tantos como areas en la ventana 
	private JPanel contentPanel_2;
	private JPanel contentPanel_3;
	private JPanel contentPanel_4;
	private JPanel contentPanel_5;
	private JMenu fileMenu; 
	private JMenu simulatorMenu; 
	private JMenu reportsMenu; 
	private JToolBar toolBar; 
	private JFileChooser fc; 
	private File currentFile;
	private JButton loadButton; 
	private JButton saveButton; 
	private JButton clearEventsButton; 
	private JButton checkInEventsButton; 
	private JButton runButton; 
	private JButton stopButton;
	private JButton resetButton; 
	private JSpinner stepsSpinner; 
	private JTextField timeViewer; 
	private JButton genReportsButton; 
	private JButton clearReportsButton; 
	private JButton saveReportsButton;
	private JButton quitButton;
	private JTextArea eventsEditor; // editor de eventos 
	private JTable eventsTable; // cola de eventos 
	private JTextArea reportsArea; // zona de informes 
	private JTable vehiclesTable; // tabla de vehiculos 
	private JTable roadsTable; // tabla de carreteras 
	private JTable junctionsTable; // tabla de cruces
	
	private JPanel panelEditor;
	private EventsTableModel eventsTableModel;
	private Border borderDefault = BorderFactory.createLineBorder(Color.BLACK, 2);
	private VehiclesTableModel vehiclesTableModel;
	private RoadsTableModel roadsTableModel;
	private JunctionsTableModel junctionsTableModel;
	private RoadMapComponent mapComponent;
	private String template;
	
	public MainWindow(TrafficSimulator model, String inFileName, Controller ctrl) {
		super("Traffic Simulator");
		this.ctrl = ctrl;
		currentFile = inFileName != null ? new File(inFileName) : null;
		reportsOutputStream = new JTextAreaOutputStream(reportsArea);
		ctrl.setOutputStream(reportsOutputStream); // ver sección 8
		initGUI();
		model.addObserver(this);
	}
	
	private void initGUI() {
		mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		
		contentPanel_1 = new JPanel();
		contentPanel_1.setLayout(new BoxLayout(contentPanel_1, BoxLayout.Y_AXIS)); // Divide la ventana en mas paneles y configura layout
		contentPanel_2 = new JPanel();
		contentPanel_2.setLayout(new BoxLayout(contentPanel_2, BoxLayout.X_AXIS));
		contentPanel_3 = new JPanel();
		contentPanel_3.setLayout(new BoxLayout(contentPanel_3, BoxLayout.X_AXIS));
		contentPanel_4 = new JPanel();
		contentPanel_4.setLayout(new BoxLayout(contentPanel_4, BoxLayout.Y_AXIS));
		contentPanel_5 = new JPanel();
		
		mainPanel.add(contentPanel_1, BorderLayout.CENTER);
		contentPanel_1.add(contentPanel_2);
		contentPanel_1.add(contentPanel_3);
		contentPanel_3.add(contentPanel_4);
		contentPanel_4.add(contentPanel_5);
		
		fc = new JFileChooser(); 
		
		addMenuBar(); // barra de menus
		addToolBar(); // barra de herramientas
		addEventsEditor(); // editor de eventos
		addEventsView(); // cola de eventos
		addReportsArea(); // zona de informes
		addVehiclesTable(); // tabla de vehiculos
		addRoadsTable(); // tabla de carreteras
		addJunctionsTable(); // tabla de cruces
		addMap(); // mapa de carreteras
		
		// Añade configuraciones de la ventana principal
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
		pack();
		setVisible(true);
	}
	
	private void addEventsEditor() { 
		// Crea un JPanel para este componente
		panelEditor = new JPanel(new BorderLayout());
		eventsEditor = new JTextArea(40,30);
		
		// Suma eventsEditor al JPanel y añade JScrollPane
		panelEditor.add(new JScrollPane(eventsEditor, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED),
				BorderLayout.CENTER);
		
		// Añade JPanel al panel correspondiente de la ventana principal
		contentPanel_2.add(panelEditor);
		
		if (currentFile != null) {
			eventsEditor.setText(read(currentFile));
		}
		
		refreshEventsAreaBorder();
		
		// Crea el PopupMenu de eventos según la figura
		JPopupMenu popupMenu = new JPopupMenu();
		
		// Crea y configura el JMenu de Add Template
		JMenu addTemplateMenu = new JMenu("Add Template");
		String[] options = {"New RR Junction", "New MC Junction", "New Junction", "New Dirt Road",
				"New Lanes Road", "New Road", "New Bike", "New Car", "New Vehicle", "Make Vehicle Faulty"};
		template = "";
		
		for (String option : options) {
			JMenuItem menuOption = new JMenuItem(option);
			menuOption.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					String[] words = option.split(" ");
					
					if (words[words.length-1].equals("Junction")) {
						template += "\n[new_junction]\n"
								+ "time = \n"
								+ "id = \n";
						
						if (!option.equals("New Junction")) {
							if (option.equals("New RR Junction")) {
								template += "max_time_slice = \n"
										+ "min_time_slice = \n";
							}
							
							template += "type = \n";
						}
					}
					else if (words[words.length-1].equals("Road")) {
						template += "\n[new_road]\n"
								+ "time = \n"
								+ "id = \n"
								+ "src = \n"
								+ "dest = \n"
								+ "max_speed = \n"
								+ "length = \n";
						
						if (!option.equals("New Road")) {
							if (option.equals("New Lanes Road")) 
								template += "lanes = \n";
							
							template += "type = \n";
						}
					}
					else if (option.equals("New Bike") || option.equals("New Car") || option.equals("New Vehicle")) {
						template += "\n[new_vehicle]\n"
								+ "time = \n"
								+ "id = \n"
								+ "itinerary = \n"
								+ "max_speed = \n";
						
						if (!option.equals("New Vehicle")) {
							if (option.equals("New Car")) {
								template += "resistence = \n"
										+ "fault_probability = \n"
										+ "max_fault_duration = \n"
										+ "seed = \n";
							}
							
							template += "type = \n";
						}
					}
					else if (option.equals("Make Vehicle Faulty")) {
						template += "\n[make_vehicle_faulty]\n"
								+ "time = \n"
								+ "vehicles = \n"
								+ "duration = \n";
					}
					
					eventsEditor.insert(template, eventsEditor.getCaretPosition());
				}
				
			});
			
			addTemplateMenu.add(menuOption);
		}
		
		// Crea y configura el JMenuItem de Load
		JMenuItem loadItem = new JMenuItem("Load");
		loadItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadFile();
			}
			
		});
		
		// Crea y configura el JMenuItem de Save
		JMenuItem saveItem = new JMenuItem("Save");
		saveItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
			
		});
		
		// Crea y configura el JMenuItem de Clear
		JMenuItem clearItem = new JMenuItem("Clear");
		clearItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				eventsEditor.setText("");
			}
			
		});
		
		// Suma los componentes creados al PopupMenu
		popupMenu.add(addTemplateMenu);
		popupMenu.addSeparator();
		popupMenu.add(loadItem);
		popupMenu.add(saveItem);
		popupMenu.add(clearItem);
		
		// Registra MouseListener en eventsEditor
		eventsEditor.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {
				showPopup(arg0);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				showPopup(arg0);
			}
			
			private void showPopup(MouseEvent e) {
				if (e.isPopupTrigger() && popupMenu.isEnabled()) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			
		});
	}
	
	private void addEventsView() { 
		// Crea un JPanel para este componente
		JPanel panelEventsView = new JPanel(new BorderLayout());
		
		// Pon el borde en el JPanel
		panelEventsView.setBorder(BorderFactory.createTitledBorder(borderDefault, "Events Queue: ", TitledBorder.LEFT, TitledBorder.TOP));
		eventsTableModel = new EventsTableModel();
		eventsTable = new JTable(eventsTableModel);
		
		// Suma eventsTable al JPanel y añade JScrollPane
		panelEventsView.add(new JScrollPane(eventsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), 
				BorderLayout.CENTER);
		
		// Suma el JPanel al panel correspondiente de la ventana principal
		contentPanel_2.add(panelEventsView);
	}
	
	private void addReportsArea() { 
		// Crea un JPanel para este componente
		JPanel panelReports = new JPanel(new BorderLayout());
		
		// Pon el borde en el JPanel
		panelReports.setBorder(BorderFactory.createTitledBorder(borderDefault, "Reports: ", TitledBorder.LEFT, TitledBorder.TOP));
		
		reportsArea = new JTextArea(40, 30);
		reportsArea.setEditable(false);
		
		// Suma reportsArea al JPanel y añade JScrollPane
		panelReports.add(new JScrollPane(reportsArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), 
				BorderLayout.CENTER);
		
		// Suma el JPanel al panel correspondiente de la ventana principal
		contentPanel_2.add(panelReports);
	}
	
	private void addVehiclesTable() { 
		// Crea un JPanel para este componente
		JPanel panelVehiclesView = new JPanel(new BorderLayout());
		
		// Pon el borde en el JPanel
		panelVehiclesView.setBorder(BorderFactory.createTitledBorder(borderDefault, "Vehicles:", TitledBorder.LEFT, TitledBorder.TOP));
		vehiclesTableModel = new VehiclesTableModel();
		vehiclesTable = new JTable(vehiclesTableModel);
		
		// Suma eventsTable al JPanel y añade JScrollPane
		panelVehiclesView.add(new JScrollPane(vehiclesTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), 
				BorderLayout.CENTER);
		
		// Suma el JPanel al panel correspondiente de la ventana principal
		contentPanel_4.add(panelVehiclesView);
	}
	
	private void addRoadsTable() { 
		// Crea un JPanel para este componente
		JPanel panelRoadsView = new JPanel(new BorderLayout());
		
		// Pon el borde en el JPanel
		panelRoadsView.setBorder(BorderFactory.createTitledBorder(borderDefault, "Roads: ", TitledBorder.LEFT, TitledBorder.TOP));
		roadsTableModel = new RoadsTableModel();
		roadsTable = new JTable(roadsTableModel);
		
		// Suma eventsTable al JPanel y añade JScrollPane
		panelRoadsView.add(new JScrollPane(roadsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), 
				BorderLayout.CENTER);
		
		// Suma el JPanel al panel correspondiente de la ventana principal
		contentPanel_4.add(panelRoadsView);
	}
	
	private void addJunctionsTable() { 
		// Crea un JPanel para este componente
		JPanel panelJunctionsView = new JPanel(new BorderLayout());
		
		// Pon el borde en el JPanel
		panelJunctionsView.setBorder(BorderFactory.createTitledBorder(borderDefault, "Junctions:", TitledBorder.LEFT, TitledBorder.TOP));
		junctionsTableModel = new JunctionsTableModel();
		junctionsTable = new JTable(junctionsTableModel);
		
		// Suma eventsTable al JPanel y añade JScrollPane
		panelJunctionsView.add(new JScrollPane(junctionsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), 
				BorderLayout.CENTER);
		
		// Suma el JPanel al panel correspondiente de la ventana principal
		contentPanel_4.add(panelJunctionsView);
	}
	
	private void addMap() {
		contentPanel_5.setBorder(BorderFactory.createTitledBorder(borderDefault, "Grafo: ", TitledBorder.LEFT, TitledBorder.TOP));
		mapComponent = new RoadMapComponent();
		
		contentPanel_5.add(new JScrollPane(mapComponent, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), 
				BorderLayout.CENTER);
		
		contentPanel_3.add(contentPanel_5);
	}
	
	public void addMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		
		// Añade fileMenu a menuBar
		menuBar.add(fileMenu);
		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		// Crea y configura JMenuItem “Load Events”
		JMenuItem loadItem = new JMenuItem("Load Events");
		loadItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadFile();
			}
			
		});
		loadItem.setMnemonic(KeyEvent.VK_L);
		loadItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.ALT_MASK));
		
		// Crea y configura JMenuItem “Save Events”
		JMenuItem saveItem = new JMenuItem("Save Events");
		saveItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveFile();
			}
			
		});
		saveItem.setMnemonic(KeyEvent.VK_S);
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
		
		// Crea y configura JMenuItem “Save Report”
		JMenuItem saveReportItem = new JMenuItem("Save Report");
		saveReportItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveReport();
			}
			
		});
		saveReportItem.setMnemonic(KeyEvent.VK_R);
		saveReportItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
		
		// Crea y configura JMenuItem “Exit”
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				exit();
			}
			
		});
		exitItem.setMnemonic(KeyEvent.VK_E);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
		
		// Suma los JMenuItems a fileMenu con Separador
		fileMenu.add(loadItem);
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		fileMenu.add(saveReportItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);
		
		simulatorMenu = new JMenu("Simulator");
		// Añade simulatorMenu a menuBar
		menuBar.add(simulatorMenu);
		simulatorMenu.setMnemonic(KeyEvent.VK_S);
		
		// Crea y configura JMenuItem “Run”
		JMenuItem runItem = new JMenuItem("Run");
		runItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(MainWindow.this, "Run pressed", "", JOptionPane.DEFAULT_OPTION);
			}
			
		});
		
		// Crea y configura JMenuItem “Reset”
		JMenuItem resetItem = new JMenuItem("Reset");
		resetItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				ctrl.reset();
			}
			
		});
		
		// Crea y configura JMenuItem “Redirect Output”
		JMenuItem redirectItem = new JMenuItem("Redirect Output");
		redirectItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(MainWindow.this, "Redirect Output pressed", "", JOptionPane.DEFAULT_OPTION);
			}
			
		});
		
		// Suma los JMenuItems a simulatorMenu
		simulatorMenu.add(runItem);
		simulatorMenu.add(resetItem);
		simulatorMenu.add(redirectItem);
		
		reportsMenu = new JMenu("Reports");
		// Añade reportsMenu a menuBar
		menuBar.add(reportsMenu);
		reportsMenu.setMnemonic(KeyEvent.VK_R);
		
		// Crea y configura JMenuItem “Generate”
		JMenuItem generateItem = new JMenuItem("Generate");
		generateItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				reportsArea.append(map.generateReport(time));
			}
			
		});
		
		// Crea y configura JMenuItem “Clear”
		JMenuItem clearItem = new JMenuItem("Clear");
		clearItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				clearEventsArea();
			}
			
		});
		
		// Suma los JMenuItems a reportsMenu
		reportsMenu.add(generateItem);
		reportsMenu.add(clearItem);
		
		this.setJMenuBar(menuBar);
	}
	
	private void addToolBar() {
		toolBar = new JToolBar();
		mainPanel.add(toolBar, BorderLayout.PAGE_START);
		
		loadButton = new JButton();
		// Configurar loadButton
		loadButton.setActionCommand("load");
		loadButton.setToolTipText("Load a selected file");
		loadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				loadFile();
			}
			
		});
		loadButton.setIcon(new ImageIcon("icons/open.png"));
		// Sumar loadButton a toolBar saveButton = new JButton();
		toolBar.add(loadButton);
		
		saveButton = new JButton();
		// Configurar saveButton
		saveButton.setActionCommand("save");
		saveButton.setToolTipText("Save the current file");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveFile();
			}
			
		});
		saveButton.setIcon(new ImageIcon("icons/save.png"));
		// Sumar saveButton toolBar
		toolBar.add(saveButton);
		
		clearEventsButton = new JButton();
		// Configurar clearEventsButton
		clearEventsButton.setActionCommand("clear");
		clearEventsButton.setToolTipText("Clears the event text");
		clearEventsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearEventsArea();
			}
			
		});
		clearEventsButton.setIcon(new ImageIcon("icons/clear.png"));
		// Sumar clearEventsButton a toolBar
		toolBar.add(clearEventsButton);
		
		checkInEventsButton = new JButton();
		// Configurar checkInEventsButton
		checkInEventsButton.setActionCommand("events");
		checkInEventsButton.setToolTipText("Check in the event text");
		checkInEventsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ctrl.loadEvents(new ByteArrayInputStream(eventsEditor.getText().getBytes()));
				} catch (IOException ioE) {
					System.err.println("ERROR: " + ioE.getMessage());
				}
			}
			
		});
		checkInEventsButton.setIcon(new ImageIcon("icons/events.png"));
		// Sumar checkInEventsButton a toolBar
		toolBar.add(checkInEventsButton);
		
		runButton = new JButton();
		// Configurar runButton
		runButton.setActionCommand("play");
		runButton.setToolTipText("Play");
		runButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ctrl.run(Integer.parseInt(stepsSpinner.getValue().toString()));
				} catch (Exception ex) {
					System.err.println("ERROR: " + ex.getMessage());
				}
			}
			
		});
		runButton.setIcon(new ImageIcon("icons/play.png"));
		// Sumar runButton a toolBar
		toolBar.add(runButton);
		
		stopButton = new JButton();
		// Configurar stopButton
		stopButton.setActionCommand("stop");
		stopButton.setToolTipText("Stops the execution");
		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Not implemented");
			}
			
		});
		stopButton.setIcon(new ImageIcon("icons/stop.png"));
		// Sumar stopButton a toolBar
		toolBar.add(stopButton);
		
		
		resetButton = new JButton();
		// Configurar resetButton
		resetButton.setActionCommand("reset");
		resetButton.setToolTipText("Reset the simulator");
		resetButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ctrl.reset();
			}
			
		});
		resetButton.setIcon(new ImageIcon("icons/reset.png"));
		// Sumar resetButton a toolBar
		toolBar.add(resetButton);
		
		toolBar.add(new JLabel(" Steps: "));
		stepsSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 1000, 1));
		// Configurar stepsSpinner
		stepsSpinner.setMaximumSize(new Dimension(70, 50));
		
		// Sumar stepsSpinner a toolBar
		toolBar.add(stepsSpinner);
		
		toolBar.add(new JLabel(" Time: "));
		timeViewer = new JTextField("0", 5);
		// Configurar timeViewer
		timeViewer.setMaximumSize(new Dimension(70, 50));
		// Sumar timeViewer a toolBar
		toolBar.add(timeViewer);
		toolBar.addSeparator();
		
		genReportsButton = new JButton();
		// Configurar genReportsButton
		genReportsButton.setActionCommand("generate_report");
		genReportsButton.setToolTipText("Generates a report with the information available");
		genReportsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reportsArea.append(map.generateReport(time));
			}
			
		});
		genReportsButton.setIcon(new ImageIcon("icons/report.png"));
		// Sumar genReportsButton a toolBar
		toolBar.add(genReportsButton);
		
		clearReportsButton = new JButton();
		// Configurar clearReportsButton
		clearReportsButton.setActionCommand("delete_report");
		clearReportsButton.setToolTipText("Deletes the current report");
		clearReportsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reportsArea.setText("");
			}
			
		});
		clearReportsButton.setIcon(new ImageIcon("icons/delete_report.png"));
		// Sumar clearReportsButton a toolBar
		toolBar.add(clearReportsButton);
		
		saveReportsButton = new JButton();
		// Configurar saveReportsButton
		saveReportsButton.setActionCommand("save_report");
		saveReportsButton.setToolTipText("Saves the current report");
		saveReportsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveReport();
			}
			
		});
		saveReportsButton.setIcon(new ImageIcon("icons/save_report.png"));
		// Sumar saveReportsButton a toolBar
		toolBar.add(saveReportsButton);
		toolBar.addSeparator();
		
		quitButton = new JButton();
		// Configurar quitButton
		quitButton.setActionCommand("exit");
		quitButton.setToolTipText("Exit");
		quitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
			
		});
		quitButton.setIcon(new ImageIcon("icons/exit.png"));
		// Sumar quitButton a toolBar
		toolBar.add(quitButton);
	}

	@Override
	public void registered(int time, RoadMap map, List<Event> events) {
		this.time = time;
		this.map = map;
		this.events = events;
		
		update();
	}

	@Override
	public void reset(int time, RoadMap map, List<Event> events) {
		this.time = time;
		this.map = map;
		this.events = events;
		
		eventsTableModel.setEvents(events);
		update();
	}

	@Override
	public void eventAdded(int time, RoadMap map, List<Event> events) {
		this.time = time;
		this.map = map;
		this.events = events;
		
		eventsTableModel.setEvents(events);
	}

	@Override
	public void advanced(int time, RoadMap map, List<Event> events) {
		this.time = time;
		this.map = map;
		this.events = events;
		
		vehiclesTableModel.setVehicles(map.getVehicles());
		roadsTableModel.setRoads(map.getRoads());
		junctionsTableModel.setJunctions(map.getJunctions());
		update();
	}

	@Override
	public void simulatorError(int time, RoadMap map, List<Event> events, SimulatorError e) {
		this.time = time;
		this.map = map;
		this.events = events;
		
		update();
	}
	
	private void refreshEventsAreaBorder() {
		if (currentFile == null)
			panelEditor.setBorder(BorderFactory.createTitledBorder(borderDefault, "Events: ", TitledBorder.LEFT, TitledBorder.TOP));
		else
			panelEditor.setBorder(BorderFactory.createTitledBorder(borderDefault, "Events: " + currentFile.getName(), TitledBorder.LEFT, TitledBorder.TOP));
	}
	
	private void write(File file, String text) {
		try {
			PrintWriter printWriter = new PrintWriter(file);
			printWriter.print(text);
			printWriter.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	private String read(File file) {
		String result = "";
		
		try {
			result = new Scanner(file).useDelimiter("\\A").next();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		return result;
	}
	
	private void loadFile() {
		if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			File eventsFile = fc.getSelectedFile();
			String eventsText = read(eventsFile);
			eventsEditor.setText(eventsText);
			currentFile = eventsFile;
		}
		
		refreshEventsAreaBorder();
	}
	
	private void saveFile() {
		if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File saveFile = fc.getSelectedFile();
			write(saveFile, eventsEditor.getText());
		}
	}
	
	private void saveReport() {
		if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File saveFile = fc.getSelectedFile();
			write(saveFile, reportsArea.getText());
		}
	}
	
	private void clearEventsArea() {
		eventsEditor.setText("");
		currentFile = null;
		refreshEventsAreaBorder();
	}
	
	private void exit() {
		if (JOptionPane.showConfirmDialog(null, "Do you really wish to exit the simulator?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
			System.exit(0);
	}
	
	private void update() {
		vehiclesTableModel.setVehicles(map.getVehicles());
		roadsTableModel.setRoads(map.getRoads());
		junctionsTableModel.setJunctions(map.getJunctions());
		eventsTableModel.setEvents(events);
		
		mapComponent.setMap(map);
		timeViewer.setText(String.valueOf(time));
	}

}
