/*
 * Copyright (c) 2013, 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.graalvm.compiler.core.common.spi;

import org.graalvm.compiler.core.common.LIRKind;
import org.graalvm.word.LocationIdentity;

import jdk.vm.ci.code.ValueKindFactory;

/**
 * Details about a set of supported {@link ForeignCallDescriptor foreign calls}.
 */
public interface ForeignCallsProvider extends ValueKindFactory<LIRKind> {

    /**
     * Determines if a given foreign call is side-effect free. Deoptimization cannot return
     * execution to a point before a foreign call that has a side effect.
     */
    boolean isReexecutable(ForeignCallDescriptor descriptor);

    /**
     * Gets the set of memory locations killed by a given foreign call. Returning the special value
     * {@link LocationIdentity#any()} denotes that the call kills all memory locations. Returning
     * any empty array denotes that the call does not kill any memory locations.
     */
    LocationIdentity[] getKilledLocations(ForeignCallDescriptor descriptor);

    /**
     * Determines if deoptimization can occur during a given foreign call.
     */
    boolean canDeoptimize(ForeignCallDescriptor descriptor);

    /**
     * Identifies foreign calls which are guaranteed to include a safepoint check.
     */
    boolean isGuaranteedSafepoint(ForeignCallDescriptor descriptor);

    /**
     * Gets the linkage for a foreign call.
     */
    ForeignCallLinkage lookupForeignCall(ForeignCallDescriptor descriptor);

    /**
     * Return true if the foreign call has a binding.
     */
    boolean isAvailable(ForeignCallDescriptor descriptor);
}
