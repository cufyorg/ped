/*
 *	Copyright 2022-2023 cufy.org and meemer.com
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *
 *	    http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package org.cufy.bson

/* ============= ------------------ ============= */

internal typealias JavaBsonArray =
        org.bson.BsonArray

/* ============= ------------------ ============= */

/**
 * Return the java version of this bson element.
 */
val BsonArray.java: JavaBsonArray
    get() = JavaBsonArray(map { it.java })

/**
 * Return the kotlin version of this bson element.
 */
val JavaBsonArray.kt: BsonArray
    get() = BsonArray { this@kt.mapTo(this) { it.kt } }

/* ============= ------------------ ============= */