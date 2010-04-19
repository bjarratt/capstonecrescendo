/*
 *  FieldTypes.h
 *  ecologylabXML
 *
 *  Created by Nabeel Shahzad on 1/11/10.
 *  Copyright 2010 Interface Ecology Lab. All rights reserved.
 *
 */

#define UNSET_TYPE					- 999
#define BAD_FIELD					- 99
#define NAMESPACE_IGNORED_ELEMENT	0
#define ATTRIBUTE                   1
#define IGNORED_ATTRIBUTE           - ATTRIBUTE
#define LEAF						2
#define NESTED_ELEMENT              3
#define IGNORED_ELEMENT             - 3
#define COLLECTION_ELEMENT          4
#define COLLECTION_SCALAR           5
#define MAP_ELEMENT                 6
#define MAP_SCALAR                  7
#define ROOT                        8
#define TEXT_ELEMENT                9
#define AWFUL_OLD_NESTED_ELEMENT    99
#define WRAPPER					    0x0a
#define TEXT_NODE_VALUE			    0x0c	
#define PSEUDO_FIELD_DESCRIPTOR	    0x0d
#define XMLNS_ATTRIBUTE			    0x0e
#define XMLNS_IGNORED			    0x0f
#define NAME_SPACE_MASK				0x10
#define NAMESPACE_TRIAL_ELEMENT		(NAME_SPACE_MASK)
#define NAME_SPACE_ATTRIBUTE		(NAME_SPACE_MASK + ATTRIBUTE)
#define NAME_SPACE_NESTED_ELEMENT	(NAME_SPACE_MASK + NESTED_ELEMENT)
#define NAME_SPACE_LEAF_NODE		(NAME_SPACE_MASK + LEAF)