// Driver ExtJs Program - PRS UI [Sakila]

/*                                                                  //
    <-------------------- Stores and Models --------------------> 
*/                                                                  //

// `Movies` Model
Ext.define('Movies', {
    extend: 'Ext.data.Model',
    pageSize : 15,
    fields: ['film_id', 'title', 'description', 'release_year', 'language', 'director', 'rating', 'special_features']
});

// `filmStore` Store Object to Extract Object after AJAX Call
var filmStore = Ext.create('Ext.data.Store', {
    storeId: 'filmTableStore',
    model: 'Movies',
    enablePaging: true,
    pageSize: 15,
    proxy: {
        type: 'ajax',
        url : '/JavaStruts/GetSakila.action',
        enablePaging: true, 
        reader: {
            type: 'json',
            transform: function(records) {
                // let filmData = row.filmData
                // row.filmData = JSON.parse(filmData)
                // return row
                let filmData = JSON.parse(records)
                return filmData
            },
            rootProperty: "filmData",
            totalProperty: "totalCount",
            successProperty: 'success'
        }
    },
    autoLoad: true,
});

// Loading Data into `filmStore` Store [Keeping Limit 15 so as to Display Scroll]
filmStore.load({               
    params: {
        start: 0,
        limit: 15
    }
})

// Reference Model to Create ComboBox for Language Dropdown
var languageModel = Ext.define('languageModel', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'languageSelection',
        type: 'string'
    }]
});

// `languageDropDown` Store Object for Language Combobox
var languageDropDown = Ext.create('Ext.data.Store', {
    model:  languageModel,
    data: [
        {'languageSelection' : "English"},
        {'languageSelection' : "French"},
        {'languageSelection' : "German"},
        {'languageSelection' : "Italian"},
        {'languageSelection' : "Japanese"},
        {'languageSelection' : "Mandarin"},
        {'languageSelection' : "Mongolian"},
        {'languageSelection' : "Hindi"}
    ],
    autoLoad: true
});

languageDropDown.load();

// Reference Model to Create ComboBox for rating Dropdown
var ratingModel = Ext.define('ratingsM', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'ratingSelection',
        type: 'string'
    }]
});

// `ratingDropDown` Store Object for rating Combobox
var ratingDropDown = Ext.create('Ext.data.Store', {
    model:  ratingModel,
    data: [
        {'ratingSelection' : "PG"},
        {'ratingSelection' : "G"},
        {'ratingSelection' : "NC-17"},
        {'ratingSelection' : "PG-13"},
        {'ratingSelection' : "PG-13"}
    ],
    autoLoad: true
});

ratingDropDown.load();

// Reference Model to Create ComboBox for Special Features Dropdown
var specialFeaturesModel = Ext.define('speacialF', {
    extend: 'Ext.data.Model',
    fields: [{
        name: 'specialFeaturesSelection',
        type: 'string'
    }]
});

// `specialFeaturesDropDown` Store Object for Special Features Combobox
var specialFeaturesDropDown = Ext.create('Ext.data.Store', { 
    model:  specialFeaturesModel,  
    data: [
        {'specialFeaturesSelection' : "Trailers"},
        {'specialFeaturesSelection' : "Deleted Scenes"},
        {'specialFeaturesSelection' : "Commentaries"},
        {'specialFeaturesSelection' : "Behind the Scenes"},
    ],
    autoLoad: true
});

specialFeaturesDropDown.load();

/*                                                        //
    <-------------------- Windows --------------------> 
*/                                                       //

// `addWindow` Window for Add Button Functionality
var addWindow = Ext.create('Ext.window.Window', {
    title: 'Add',
    width: 400,
    layout: 'fit',
    autoDestroy: false,
    closeAction: 'close',
    items: [{ 
        xtype: 'form',
        bodyPadding: 5,
        items: [{
            xtype : 'textfield',
            fieldLabel: 'Title',
            name: 'title',
            id: 'movie_title'
        }, {
            xtype : 'textarea',
            fieldLabel: 'Description',
            name: 'description',
            id: 'movie_description'
        }, {
            xtype : 'textfield',
            fieldLabel: 'Release Year',
            name: 'release_year',
            id: 'movie_release_year'
        }, {
            xtype: 'combobox',
            fieldLabel: 'Language',
            store: languageDropDown,
            queryMode: 'local',
            displayField: 'languageSelection',
            name: 'language',
            id: 'movie_language'
        }, {
            xtype : 'textfield',
            fieldLabel: 'Director',
            name: 'director',
            id: 'movie_director'
        }, {
            xtype: 'combobox',
            fieldLabel: 'Rating',
            store: ratingDropDown,
            queryMode: 'local',
            displayField: 'ratingSelection',
            name: 'rating',
            id: 'movie_rating'
        }, {
            xtype: 'combobox',
            fieldLabel: 'Special Features',
            store: specialFeaturesDropDown,
            queryMode: 'local',
            displayField: 'specialFeaturesSelection',
            name: 'special_features',
            id: 'movie_special_features'
        }],
        
        buttons: [{
            text: 'Add',
            iconCls: 'x-fa fa-plus-circle',
            handler: function(){

                var form = this.up('form').getForm()
                var addFormValues = form.getValues()
                var addFormEncoded = Ext.encode(addFormValues)
                console.log("Add Button: " + addFormEncoded)
                if (form.isValid()) {
                    Ext.Ajax.request({
                        url: '/JavaStruts/AddSakila.action',
                        method: 'POST',
                        params: addFormValues,
                        success: function (response) {
                            filmStore.load({
                                params: {
                                    start: 0,
                                    limit: 15
                                }
                            });
                            addWindow.close();
                            // this.up('form').getForm().reset();
                            addWindow.down('form').getForm().reset();
                        },
                        failure: function (response) {
                            Ext.Msg.alert('Request Failed', 'Oops, Please Try Again!');
                        }
                    });
                }
            }
        }, {
            text: 'Cancel',
            iconCls: 'x-fa fa-times',
            handler: function(){
                addWindow.close();
            }
        }],
        buttonAlign: 'center',
    }]
});

// `editWindow` Window for Edit Button Functionality
var editWindow = Ext.create('Ext.window.Window', {
    title: 'Edit',
    width: 400,
    layout: 'fit',
    autoDestroy: false,
    closeAction: 'close',
    items: [{ 
        xtype: 'form',
        bodyPadding: 5,
        id: 'editFormId',
        items: [{
            xtype : 'textfield',
            fieldLabel: 'Film ID',
            name: 'film_id',
            id: 'editFilmId'
        }, {
            xtype : 'textfield',
            fieldLabel: 'Title',
            name: 'title',
            id: 'editTitle'
        }, {
            xtype : 'textarea',
            fieldLabel: 'Description',
            name: 'description',
            id: 'editDescription'
        }, {
            xtype : 'textfield',
            fieldLabel: 'Release Year',
            name: 'release_year',
            id: 'editReleaseYear'
        }, {
            xtype: 'combobox',
            fieldLabel: 'Language',
            store: languageDropDown,
            queryMode: 'local',
            displayField: 'languageSelection',
            name: 'language',
            id: 'editLanguage'
        }, {
            xtype : 'textfield',
            fieldLabel: 'Director',
            name: 'director',
            id: 'editDirector'
        }, {
            xtype: 'combobox',
            fieldLabel: 'Rating',
            store: ratingDropDown,
            queryMode: 'local',
            displayField: 'ratingSelection',
            name: 'rating',
            id: 'editFilmRating'
        }, {
            xtype: 'combobox',
            fieldLabel: 'Special Features',
            store: specialFeaturesDropDown,
            queryMode: 'local',
            displayField: 'specialFeaturesSelection',
            name: 'special_features',
            id: 'editSpecialFeatures'
        }],
        
        buttons: [{
            text: 'Edit',
            iconCls: 'x-fa fa-pencil-square-o',
            handler: function(){

                Ext.getCmp('editFilmId').enable();
                var form = this.up('form').getForm();
                var editFormValues = form.getValues();
                var editFormEncoded = Ext.encode(editFormValues);
                console.log("Edit Form: " + editFormEncoded);
                if (form.isValid()) {
                    Ext.Ajax.request({
                        url: '/JavaStruts/EditSakila.action',
                        method: 'POST',
                        params: editFormValues,
                        success: function (response) {
                            filmStore.load({
                                params: {
                                    start: 0,
                                    limit: 15
                                }
                            });
                            editWindow.close();
                            // this.up('form').getForm().reset();
                            editWindow.down('form').getForm().reset();
                        },
                        failure: function (response) {
                            Ext.Msg.alert('Request Failed', 'Oops, Please Try Again!');
                        }
                    });
                }
            }
        }, {
            text: 'Cancel',
            iconCls: 'x-fa fa-times',
            handler: function(){
                editWindow.close();
            }
        }],
        buttonAlign: 'center',
    }]
});

// `deleteWindow` Window for Delete Button Functionality
var deleteWindow = Ext.create('Ext.window.Window', {
    title: 'Delete',
    width: 400,
    html: "<p>Are you sure you want to Delete!?</p>",
    layout: 'fit',
    autoDestroy: false,
    closeAction: 'close',
    items: [{ 
        xtype: 'form',
        bodyPadding: 5,
        id: 'deleteFormId',
        items: [{
            xtype : 'textfield',
            fieldLabel: 'Film ID',
            name: 'del_filmIds',
            id: 'deleteFilmId'
        }],
        
        buttons: [{
            text: 'Delete',
            iconCls: 'x-fa fa-trash',
            handler: function(){
 
                Ext.getCmp('deleteFilmId').enable();
                var form = this.up('form').getForm();
                var deleteFormValues = form.getValues()
                var deleteFormEncoded = Ext.encode(deleteFormValues);
                console.log("Delete Form: " + deleteFormEncoded);
                if (form.isValid()) {
                    Ext.Ajax.request({
                        url: '/JavaStruts/DeleteSakila.action',
                        method: 'POST',
                        params: deleteFormValues,
                        success: function (response) {
                            filmStore.load({
                                params: {
                                    start: 0,
                                    limit: 15
                                }
                            });
                            deleteWindow.close();
                            // this.up('form').getForm().reset();
                            deleteWindow.down('form').getForm().reset();
                        },
                        failure: function (response) {
                            Ext.Msg.alert('Request Failed', 'Oops, Please Try Again!');
                        }
                    });
                }
            }
        }, {
            text: 'Cancel',
            iconCls: 'x-fa fa-times',
            handler: function(){
                deleteWindow.close();
            }
        }],
        buttonAlign: 'center',
    }]
});

// Main UI Rendering Function
Ext.onReady(function () {
    Ext.create('Ext.container.Viewport', {
        "requires": [
            "font-pictos"
        ],
        layout: {
            type: 'fit',
            pack: 'center',
            align: 'middle'
        },
        items: [{
            xtype: 'panel',
            layout: {
                type: 'border',
                pack: 'center',
                align: 'middle'
            },
            items: [{
                title: 'Movie Advance Search',
                region: 'center',
                xtype: 'panel',
                layout: {
                    type: 'fit',
                    pack: 'center',
                    align: 'middle'
                },
                margin: '5 5 5 5',
                items: [{
                    xtype: 'form',
                    bodyPadding: 5,
                    autoScroll: true,
                    buttonAlign: 'center',
                    layout: {
                        type: 'anchor',
                        pack: 'center',
                        align: 'middle'
                    },
                    defaults: {
                        anchor: '100%'
                    },
                    url: 'add_item',
                    defaultType: 'textfield',
                    items: [{
                        xtype: 'fieldcontainer',
                        margin: '20 0 0 0',
                        bodyPadding: 5,
                        layout: {
                            type: 'hbox',
                            pack: 'center',
                            align: 'middle'
                        },
                        items: [{
                            xtype: 'textfield',
                            fieldLabel: 'Movie Name',
                            name: 'title',
                            id: 'movieName'
                        }, {
                            xtype: 'splitter',
                            margin: '0 10 0 10'
                        }, {
                            xtype: 'textfield',
                            fieldLabel: 'Director',
                            name: 'director',
                            id: 'directorName'
                        }]
                    }, {
                        xtype: 'fieldcontainer',
                        margin: '10 0 0 0',
                        bodyPadding: 5,
                        layout: {
                            type: 'hbox',
                            pack: 'center',
                            align: 'middle'
                        },
                        items: [{
                            xtype: 'datefield',
                            format: 'Y',
                            // xtype: 'textfield',
                            fieldLabel: 'Release Year',
                            name: 'release_year',
                            id: 'releaseYear'
                        }, {
                            xtype: 'splitter',
                            margin: '0 10 0 10'
                        }, {
                            xtype: 'combobox',
                            fieldLabel: 'Language',
                            store: languageDropDown,
                            queryMode: 'local',
                            displayField: 'languageSelection',
                            name: 'language',
                            id: 'language_combo'
                        }]
                    }],
                    buttons: [{
                        text: 'Search',
                        formBind: true,
                        disabled: true,
                        enableToggle: false,
                        iconCls: 'x-fa fa-search',
                        handler: function () {

                            var form = this.up('form').getForm();
                            filmStore.clearFilter()
                            var formInfo = {
                                movieName_: Ext.getCmp('movieName').getValue(),
                                directorName_: Ext.getCmp('directorName').getValue(),
                                releaseYear_: (Ext.getCmp('releaseYear').getValue() != null || Ext.getCmp('releaseYear').getValue() != "") ? Ext.getCmp('releaseYear').getValue().getFullYear() : 2006,
                                // releaseYear_: Ext.getCmp('releaseYear').getValue(),
                                language_: Ext.getCmp('language_combo').getValue()
                            }

                            console.log("Search Form: " + formInfo)

                            // Using Servlet [via Backend]
                            /*
                            form.submit({
                                url: '/JavaStruts/GetFormData',
                                method: 'POST',
                                params: formInfo,
                                success: function(response) {
                                    Ext.Msg.alert('Success', "Records Found!");
                                    filmStore.load({
                                        params: {
                                            start: 0,
                                            limit: 15
                                        }
                                    })
                                },
                                failure: function(response) {
                                    Ext.Msg.alert('Failed', "No Records Found!");
                                }
                            });
                            */

                            // Manual (UI Based)
                            if (!formInfo.movieName_ && !formInfo.directorName_ && !formInfo.releaseYear_ && !formInfo.language_) {
                                Ext.Msg.alert("Blank Form", "You've submitted a Blank Form!")
                            } 
                            else if (formInfo.movieName_ && !formInfo.directorName_ && formInfo.releaseYear_ && !formInfo.language_) {
                                filmStore.load().filter('title', formInfo.movieName_);
                            } 
                            else if (!formInfo.movieName_ && formInfo.directorName_ && !formInfo.releaseYear_ && !formInfo.language_) {
                                filmStore.load().filter('director', formInfo.directorName_);
                            } 
                            else if (!formInfo.movieName_ && !formInfo.directorName_ && formInfo.releaseYear_ && !formInfo.language_) {                                   
                                filmStore.load().filter('release_year', formInfo.releaseYear_);
                            } 
                            else if (!formInfo.movieName_ && !formInfo.directorName_ && !formInfo.releaseYear_ && formInfo.language_) {                                 
                                filmStore.load().filter('language', formInfo.language_);
                            } 
                            else if(formInfo.movieName_ && formInfo.directorName_ && formInfo.releaseYear_ && formInfo.language_) {
                                var filtersSearch = [
                                    new Ext.util.Filter({
                                        filterFn: function(item) {
                                            return item.get('title') == formInfo.movieName_ && item.get('director') == formInfo.directorName_ && item.get('release_year') == formInfo.releaseYear_ && item.get('language') == formInfo.language_;
                                        }
                                    })
                                ];
                                filmStore.load().filter(filtersSearch);
                            }
                            else {
                                Ext.Msg.alert("Multiple Values Error", "Application Still in Development!")
                            }
                        }
                    }, {
                        text: 'Reset',
                        iconCls: 'x-fa fa-refresh',
                        handler: function () {
                            filmStore.clearFilter();
                            filmStore.load({
                                params: {
                                    start: 0,
                                    limit: 15
                                }
                            });
                            this.up('form').getForm().reset();
                        }
                    }]
                }]
            }, {
                // Movie Grid
                title: 'Movie Grid',
                id: 'movie_grid',
                name: 'movieGrid',
                region: 'south',
                xtype: 'grid',
                height: '60%',
                minHeight: 100,
                split: true,
                margin: '0 5 5 5',
                store: Ext.data.StoreManager.lookup('filmTableStore'),
                columns: [{
                    text: 'Film Id',
                    flex: 1,
                    dataIndex: 'film_id'
                }, {
                    text: 'Title',
                    flex: 2,
                    dataIndex: 'title'
                }, {
                    text: 'Description',
                    flex: 1,
                    dataIndex: 'description'
                }, {
                    text: 'Release year',
                    flex: 1,
                    dataIndex: 'release_year'
                }, {
                    text: 'Language',
                    flex: 1,
                    dataIndex: 'language'
                }, {
                    text: 'Director',
                    flex: 1,
                    dataIndex: 'director'
                },  {
                    text: 'Rating',
                    flex: 1,
                    dataIndex: 'rating'
                }, {
                    text: 'Special Feature',
                    flex: 1,
                    dataIndex: 'special_features'
                }],

                selModel: {
                    selType: 'checkboxmodel',
                    mode: 'MULTI',
                    checkOnly: true
                },

                listeners: {
                    'select': function() {
                        var gridData = {}
                        var selected = Ext.getCmp('movie_grid').getSelectionModel().getSelection();
                        if(selected.length == 1) {
                            Ext.getCmp('editButtonId').setDisabled(false);
                            Ext.getCmp('deleteButtonId').setDisabled(false);
                            gridData = selected[0].data;
                            Ext.getCmp('editFormId').getForm().setValues(gridData);
                            Ext.getCmp('deleteFormId').getForm().setValues(gridData);
                            Ext.getCmp('editFilmId').disable();
                        } 
                        else if(selected.length > 1) {
                            Ext.getCmp('editButtonId').setDisabled(true);
                            Ext.getCmp('deleteButtonId').setDisabled(false);
                            gridFilmIdList = [];
                            for(let i = 0; i < selected.length; i++) {
                                gridFilmIdList.push(selected[i].data.film_id)
                            }
                            Ext.getCmp('deleteFilmId').setValue(gridFilmIdList);
                            Ext.getCmp('deleteFilmId').disable();
                        }
                        else {
                            Ext.getCmp('editButtonId').setDisabled(true);
                            Ext.getCmp('deleteButtonId').setDisabled(true);
                        }
                    },
                
                    'deselect': function() {
                        var selected = Ext.getCmp('movie_grid').getSelectionModel().getSelection();
                        if (selected.length == 0) {
                            Ext.getCmp('editButtonId').setDisabled(true);
                            Ext.getCmp('deleteButtonId').setDisabled(true);
                        } 
                        else if (selected.length == 1) {
                            Ext.getCmp('editButtonId').setDisabled(false);
                            Ext.getCmp('deleteButtonId').setDisabled(false);
                        } 
                        else if (selected.length > 1) {
                            Ext.getCmp('editButtonId').setDisabled(true);
                            Ext.getCmp('deleteButtonId').setDisabled(false);
                        }
                    }
                },

                dockedItems: [{
                    xtype: 'pagingtoolbar',
                    store: Ext.data.StoreManager.lookup('filmTableStore'), 
                    dock: 'top',
                    displayInfo: true,
                    displayMsg: 'Displaying: {0} to {1} out of {2} &nbsp;Records ',
                    emptyMsg: "No Records to Display!&nbsp;",
                    inputItemWidth: 50,
                    items: ['->', {
                        xtype: 'button',
                        text: 'Add',
                        id: 'addButtonId',
                        iconCls: 'x-fa fa-plus-circle',
                        disabled: false,
                        listeners: {
                            click: function() {
                                addWindow.show();
                            }
                        }
                    }, '-', {
                        xtype: 'button',
                        text: 'Edit',
                        id: 'editButtonId',
                        iconCls: 'x-fa fa-pencil-square-o', 
                        disabled: true,
                        listeners: {
                            click: function() {
                                editWindow.show();
                            }
                        }
                    }, '-', {
                        xtype: 'button',
                        text: 'Delete',
                        disabled: true,
                        id: 'deleteButtonId',
                        iconCls: 'x-fa fa-trash', 
                        listeners: {
                            click: function() {
                                deleteWindow.show();
                            }
                        }
                    }]
                }]
            }]
        }],
        renderTo: 'sakilaBody'
    });
});
