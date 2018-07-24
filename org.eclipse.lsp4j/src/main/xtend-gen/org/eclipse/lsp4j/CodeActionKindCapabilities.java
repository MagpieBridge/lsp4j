/**
 * Copyright (c) 2016 TypeFox GmbH (http://www.typefox.io) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.lsp4j;

import java.util.List;
import org.eclipse.xtext.xbase.lib.Pure;
import org.eclipse.xtext.xbase.lib.util.ToStringBuilder;

@SuppressWarnings("all")
public class CodeActionKindCapabilities {
  /**
   * The code action kind values the client supports. When this
   * property exists the client also guarantees that it will
   * handle values outside its set gracefully and falls back
   * to a default value when unknown.
   * 
   * See {@link CodeActionKind} for allowed values.
   */
  private List<String> valueSet;
  
  public CodeActionKindCapabilities() {
  }
  
  public CodeActionKindCapabilities(final List<String> valueSet) {
    this.valueSet = valueSet;
  }
  
  /**
   * The code action kind values the client supports. When this
   * property exists the client also guarantees that it will
   * handle values outside its set gracefully and falls back
   * to a default value when unknown.
   * 
   * See {@link CodeActionKind} for allowed values.
   */
  @Pure
  public List<String> getValueSet() {
    return this.valueSet;
  }
  
  /**
   * The code action kind values the client supports. When this
   * property exists the client also guarantees that it will
   * handle values outside its set gracefully and falls back
   * to a default value when unknown.
   * 
   * See {@link CodeActionKind} for allowed values.
   */
  public void setValueSet(final List<String> valueSet) {
    this.valueSet = valueSet;
  }
  
  @Override
  @Pure
  public String toString() {
    ToStringBuilder b = new ToStringBuilder(this);
    b.add("valueSet", this.valueSet);
    return b.toString();
  }
  
  @Override
  @Pure
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    CodeActionKindCapabilities other = (CodeActionKindCapabilities) obj;
    if (this.valueSet == null) {
      if (other.valueSet != null)
        return false;
    } else if (!this.valueSet.equals(other.valueSet))
      return false;
    return true;
  }
  
  @Override
  @Pure
  public int hashCode() {
    return 31 * 1 + ((this.valueSet== null) ? 0 : this.valueSet.hashCode());
  }
}
