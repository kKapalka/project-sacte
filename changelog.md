# Personal Programming Challenge 1: Resume Generator (JavaFX), codename SACTE (Subject Aggregate / Curated Text Exporter)

# Changelog

### 22.02.2021
* Removed most of 'magical values' - converted them into parameterable static final values
* Added new buttons and functionalities to 'config-section' view
* Replaced old, ugly println() calls with new, dope log.debug() and log.error() calls
* Added a fail-safe mechanism for NPE handling during document creation: if the NPE handling method is called too many times, a RuntimeException will be thrown
* Now, user can actually set the title and subtitle of his text sections, and save/export them
* Also added working slf4j dependencies

### 14.10.2020
* Tested PDF generation itself, in order to ensure everything is generated properly
* Reintroduced mock config, placed it in .json file inside this project
* Corrected PDF generation method - now it will only print selected text sections
* Corrected the conditional covering method 

### 13.10.2020
* Added transition back from textSectionList to TextSection, and button, and conditional hiding
* Added creation of FontPresetList in newly created TextSections

### 12.10.2020
* Added Text Section Lists handling of single click, doubleClick
* Added handling of 'Add New Section', 'move to left' and 'move to right' buttons
* Added transition into child Text Section

### 10.10.2020
* Added comments for most of the Pdf representation classes
* added comments for both main .fxml file controllers
* separated a method for setting up font preset list view into a class which those file controllers extend
* refactored TextSection and TextSectionList
* created a FontPresetListContainer interface to be used inside this new class
* beautified FontPresetPickerDialogBoxController class
* added comments to the rest of classes

### 13.09.2020
* Added labels and buttons to 'config-list.fxml'
* succesfully saved and loaded PdfCreationConfiguration class, created from an empty object and set using UI controls
* added 'isSelected' boolean parameter to TextSection, to inform the user whether this textSection has been selected for PDF exporting

### 12.09.2020
* Added functionality of setting TextSectionListType via ChoiceBox


### 13.08.2020
* Started moving FXML elements to their proper controllers
* Created new enums: TitleHandlerType and TextSectionListType, serving a purpose of factory method for ITitleHandler and TextSectionList, respectively

### 12.08.2020
* Implemented FontPreset setter dialog box
* Added restrictions on the dialog regarding permitted input (digits only)
* Added labels to dialog box
* Changes on the presets change the resulting text appropriately

### 11.08.2020
* Started work on the FXML layout files
* Introduced a fail-safe mechanism for a situation where font set on GridTextSections would cause the 'cells' to be too large for the 'table' to be accomodated and produce NullPointerException
* Re-engineered the FontPreset assignment. From now on, it will be MainSection's duty to assign all FontPresets to its children unless a specific child will contain a specific fontPreset
* Extracted a FontPreset setting layout, and hooked it up to ListView for fontPresets (which contains a list of presets represented as simple Strings)
* Started to work on FontPreset layout window, with ability to choose font size and color for each font preset

### 26.07.2020
* Refactored a few key content assortment classes to improve reusability
* Created a new Content type class, to present title and subtitle side by side, divided by a divider of choice (NOT)
* Created TitleHandlers, dictating how both title and subtitle would be displayed in a given textSection. Types: TopToDown(standard), SideToSide(to the sides of a given text space, divided by a line of some sort), and TwoColumn (situated inside a two-cell table with specified cell width ratio respective to one another)

### 06.07.2020
* Added a feature of reading/writing complete pdf creation configuration as a JSON file
* Added a menu, where save/load/export functionalities were moved
* Changed a method for storing font color data
* Moved mock CreationConfiguration to separate class
* Finally decided on a name for this piece of software

### 03.07.2020
* Changed a method for storing font size and color data
* Added a feature of printing subsections as an ordered list

### 02.07.2020
* Added line separation to sections
* Added a feature of printing subsections as an N-column grid

### 30.06.2020
* Succesfully printed some content using newly created classes
* Prepared a StandardFontCreator class to manage the font used for resume, and set proper encoding to allow for printing polish characters
* Adjusted DisplayableString class to include font size, and font color
* Meddled in paragraph leading (fixed and multiplied), allowing for more formatting control
* added line separation option for MainSection class

### 28.06.2020
* Started preparing the classes for sections of the resume

### 26.06.2020
* Succesfully created a first PDF file
* Added functionality to save the newly created PDF file via standard FileChooser

### 25.06.2020
* Hooked up Maven to JavaFX project, and added dependencies required for PDF generation
* Also added dependencies for JavaFX itself