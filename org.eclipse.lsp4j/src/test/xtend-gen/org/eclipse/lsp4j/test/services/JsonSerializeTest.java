/**
 * Copyright (c) 2016 TypeFox and others.
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0,
 * or the Eclipse Distribution License v. 1.0 which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 * 
 * SPDX-License-Identifier: EPL-2.0 OR BSD-3-Clause
 */
package org.eclipse.lsp4j.test.services;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.CodeActionCapabilities;
import org.eclipse.lsp4j.CodeLens;
import org.eclipse.lsp4j.CodeLensCapabilities;
import org.eclipse.lsp4j.ColorProviderCapabilities;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionCapabilities;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemCapabilities;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionItemKindCapabilities;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.DefinitionCapabilities;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.DocumentHighlightCapabilities;
import org.eclipse.lsp4j.DocumentLinkCapabilities;
import org.eclipse.lsp4j.DocumentSymbolCapabilities;
import org.eclipse.lsp4j.FormattingCapabilities;
import org.eclipse.lsp4j.FormattingOptions;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.HoverCapabilities;
import org.eclipse.lsp4j.ImplementationCapabilities;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.MarkedString;
import org.eclipse.lsp4j.MarkupContent;
import org.eclipse.lsp4j.MarkupKind;
import org.eclipse.lsp4j.OnTypeFormattingCapabilities;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.RangeFormattingCapabilities;
import org.eclipse.lsp4j.ReferencesCapabilities;
import org.eclipse.lsp4j.RenameCapabilities;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.SignatureHelpCapabilities;
import org.eclipse.lsp4j.SignatureInformationCapabilities;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.SymbolKindCapabilities;
import org.eclipse.lsp4j.SynchronizationCapabilities;
import org.eclipse.lsp4j.TextDocumentClientCapabilities;
import org.eclipse.lsp4j.TextDocumentContentChangeEvent;
import org.eclipse.lsp4j.TextDocumentIdentifier;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.TypeDefinitionCapabilities;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.WorkspaceClientCapabilities;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.json.JsonRpcMethod;
import org.eclipse.lsp4j.jsonrpc.json.MessageJsonHandler;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.jsonrpc.messages.Message;
import org.eclipse.lsp4j.jsonrpc.messages.NotificationMessage;
import org.eclipse.lsp4j.jsonrpc.messages.RequestMessage;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseError;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseErrorCode;
import org.eclipse.lsp4j.jsonrpc.messages.ResponseMessage;
import org.eclipse.lsp4j.jsonrpc.services.ServiceEndpoints;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.test.services.LineEndings;
import org.eclipse.lsp4j.test.services.MessageMethods;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("all")
public class JsonSerializeTest {
  private static class TestObject {
    double foo = 12.3;
    
    String bar = "qwertz";
  }
  
  private MessageJsonHandler jsonHandler;
  
  @Before
  public void setup() {
    final Map<String, JsonRpcMethod> methods = ServiceEndpoints.getSupportedMethods(LanguageServer.class);
    final Consumer<GsonBuilder> _function = (GsonBuilder it) -> {
      it.setPrettyPrinting();
    };
    MessageJsonHandler _messageJsonHandler = new MessageJsonHandler(methods, _function);
    this.jsonHandler = _messageJsonHandler;
  }
  
  private void assertSerialize(final Message message, final CharSequence expected) {
    Assert.assertEquals(expected.toString().trim(), LineEndings.toSystemLineEndings(this.jsonHandler.serialize(message)));
  }
  
  @Test
  public void testCompletion() {
    RequestMessage _requestMessage = new RequestMessage();
    final Procedure1<RequestMessage> _function = (RequestMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("1");
      it.setMethod(MessageMethods.DOC_COMPLETION);
      TextDocumentPositionParams _textDocumentPositionParams = new TextDocumentPositionParams();
      final Procedure1<TextDocumentPositionParams> _function_1 = (TextDocumentPositionParams it_1) -> {
        TextDocumentIdentifier _textDocumentIdentifier = new TextDocumentIdentifier("file:///tmp/foo");
        it_1.setTextDocument(_textDocumentIdentifier);
        Position _position = new Position(4, 22);
        it_1.setPosition(_position);
      };
      TextDocumentPositionParams _doubleArrow = ObjectExtensions.<TextDocumentPositionParams>operator_doubleArrow(_textDocumentPositionParams, _function_1);
      it.setParams(_doubleArrow);
    };
    final RequestMessage message = ObjectExtensions.<RequestMessage>operator_doubleArrow(_requestMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"1\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"method\": \"textDocument/completion\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"textDocument\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"uri\": \"file:///tmp/foo\"");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"position\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"line\": 4,");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"character\": 22");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testInit() {
    RequestMessage _requestMessage = new RequestMessage();
    final Procedure1<RequestMessage> _function = (RequestMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("1");
      it.setMethod(MessageMethods.INITIALIZE);
      InitializeParams _initializeParams = new InitializeParams();
      final Procedure1<InitializeParams> _function_1 = (InitializeParams it_1) -> {
        it_1.setRootUri("file:///tmp/foo");
      };
      InitializeParams _doubleArrow = ObjectExtensions.<InitializeParams>operator_doubleArrow(_initializeParams, _function_1);
      it.setParams(_doubleArrow);
    };
    final RequestMessage message = ObjectExtensions.<RequestMessage>operator_doubleArrow(_requestMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"1\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"method\": \"initialize\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"processId\": null,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"rootUri\": \"file:///tmp/foo\"");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testInitClientCapabilities() {
    RequestMessage _requestMessage = new RequestMessage();
    final Procedure1<RequestMessage> _function = (RequestMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("1");
      it.setMethod(MessageMethods.INITIALIZE);
      InitializeParams _initializeParams = new InitializeParams();
      final Procedure1<InitializeParams> _function_1 = (InitializeParams it_1) -> {
        it_1.setRootUri("file:///tmp/foo");
        ClientCapabilities _clientCapabilities = new ClientCapabilities();
        final Procedure1<ClientCapabilities> _function_2 = (ClientCapabilities it_2) -> {
          TextDocumentClientCapabilities _textDocumentClientCapabilities = new TextDocumentClientCapabilities();
          final Procedure1<TextDocumentClientCapabilities> _function_3 = (TextDocumentClientCapabilities it_3) -> {
            SynchronizationCapabilities _synchronizationCapabilities = new SynchronizationCapabilities();
            final Procedure1<SynchronizationCapabilities> _function_4 = (SynchronizationCapabilities it_4) -> {
              it_4.setDynamicRegistration(Boolean.valueOf(false));
              it_4.setWillSave(Boolean.valueOf(true));
              it_4.setWillSaveWaitUntil(Boolean.valueOf(false));
              it_4.setDidSave(Boolean.valueOf(true));
            };
            SynchronizationCapabilities _doubleArrow = ObjectExtensions.<SynchronizationCapabilities>operator_doubleArrow(_synchronizationCapabilities, _function_4);
            it_3.setSynchronization(_doubleArrow);
            CompletionCapabilities _completionCapabilities = new CompletionCapabilities();
            final Procedure1<CompletionCapabilities> _function_5 = (CompletionCapabilities it_4) -> {
              it_4.setDynamicRegistration(Boolean.valueOf(false));
              CompletionItemCapabilities _completionItemCapabilities = new CompletionItemCapabilities();
              final Procedure1<CompletionItemCapabilities> _function_6 = (CompletionItemCapabilities it_5) -> {
                it_5.setSnippetSupport(Boolean.valueOf(true));
                it_5.setCommitCharactersSupport(Boolean.valueOf(true));
                it_5.setDocumentationFormat(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(MarkupKind.PLAINTEXT, MarkupKind.MARKDOWN)));
              };
              CompletionItemCapabilities _doubleArrow_1 = ObjectExtensions.<CompletionItemCapabilities>operator_doubleArrow(_completionItemCapabilities, _function_6);
              it_4.setCompletionItem(_doubleArrow_1);
              CompletionItemKindCapabilities _completionItemKindCapabilities = new CompletionItemKindCapabilities();
              final Procedure1<CompletionItemKindCapabilities> _function_7 = (CompletionItemKindCapabilities it_5) -> {
                it_5.setValueSet(Collections.<CompletionItemKind>unmodifiableList(CollectionLiterals.<CompletionItemKind>newArrayList(CompletionItemKind.Method, CompletionItemKind.Function)));
              };
              CompletionItemKindCapabilities _doubleArrow_2 = ObjectExtensions.<CompletionItemKindCapabilities>operator_doubleArrow(_completionItemKindCapabilities, _function_7);
              it_4.setCompletionItemKind(_doubleArrow_2);
              it_4.setContextSupport(Boolean.valueOf(false));
            };
            CompletionCapabilities _doubleArrow_1 = ObjectExtensions.<CompletionCapabilities>operator_doubleArrow(_completionCapabilities, _function_5);
            it_3.setCompletion(_doubleArrow_1);
            HoverCapabilities _hoverCapabilities = new HoverCapabilities();
            final Procedure1<HoverCapabilities> _function_6 = (HoverCapabilities it_4) -> {
              it_4.setDynamicRegistration(Boolean.valueOf(false));
              it_4.setContentFormat(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(MarkupKind.PLAINTEXT, MarkupKind.MARKDOWN)));
            };
            HoverCapabilities _doubleArrow_2 = ObjectExtensions.<HoverCapabilities>operator_doubleArrow(_hoverCapabilities, _function_6);
            it_3.setHover(_doubleArrow_2);
            SignatureHelpCapabilities _signatureHelpCapabilities = new SignatureHelpCapabilities();
            final Procedure1<SignatureHelpCapabilities> _function_7 = (SignatureHelpCapabilities it_4) -> {
              it_4.setDynamicRegistration(Boolean.valueOf(false));
              SignatureInformationCapabilities _signatureInformationCapabilities = new SignatureInformationCapabilities();
              final Procedure1<SignatureInformationCapabilities> _function_8 = (SignatureInformationCapabilities it_5) -> {
                it_5.setDocumentationFormat(Collections.<String>unmodifiableList(CollectionLiterals.<String>newArrayList(MarkupKind.PLAINTEXT, MarkupKind.MARKDOWN)));
              };
              SignatureInformationCapabilities _doubleArrow_3 = ObjectExtensions.<SignatureInformationCapabilities>operator_doubleArrow(_signatureInformationCapabilities, _function_8);
              it_4.setSignatureInformation(_doubleArrow_3);
            };
            SignatureHelpCapabilities _doubleArrow_3 = ObjectExtensions.<SignatureHelpCapabilities>operator_doubleArrow(_signatureHelpCapabilities, _function_7);
            it_3.setSignatureHelp(_doubleArrow_3);
            ReferencesCapabilities _referencesCapabilities = new ReferencesCapabilities();
            final Procedure1<ReferencesCapabilities> _function_8 = (ReferencesCapabilities it_4) -> {
              it_4.setDynamicRegistration(Boolean.valueOf(false));
            };
            ReferencesCapabilities _doubleArrow_4 = ObjectExtensions.<ReferencesCapabilities>operator_doubleArrow(_referencesCapabilities, _function_8);
            it_3.setReferences(_doubleArrow_4);
            DocumentHighlightCapabilities _documentHighlightCapabilities = new DocumentHighlightCapabilities();
            final Procedure1<DocumentHighlightCapabilities> _function_9 = (DocumentHighlightCapabilities it_4) -> {
              it_4.setDynamicRegistration(Boolean.valueOf(false));
            };
            DocumentHighlightCapabilities _doubleArrow_5 = ObjectExtensions.<DocumentHighlightCapabilities>operator_doubleArrow(_documentHighlightCapabilities, _function_9);
            it_3.setDocumentHighlight(_doubleArrow_5);
            DocumentSymbolCapabilities _documentSymbolCapabilities = new DocumentSymbolCapabilities();
            final Procedure1<DocumentSymbolCapabilities> _function_10 = (DocumentSymbolCapabilities it_4) -> {
              it_4.setDynamicRegistration(Boolean.valueOf(false));
              SymbolKindCapabilities _symbolKindCapabilities = new SymbolKindCapabilities();
              final Procedure1<SymbolKindCapabilities> _function_11 = (SymbolKindCapabilities it_5) -> {
                it_5.setValueSet(Collections.<SymbolKind>unmodifiableList(CollectionLiterals.<SymbolKind>newArrayList(SymbolKind.Module, SymbolKind.Namespace, SymbolKind.Package, SymbolKind.Class)));
              };
              SymbolKindCapabilities _doubleArrow_6 = ObjectExtensions.<SymbolKindCapabilities>operator_doubleArrow(_symbolKindCapabilities, _function_11);
              it_4.setSymbolKind(_doubleArrow_6);
            };
            DocumentSymbolCapabilities _doubleArrow_6 = ObjectExtensions.<DocumentSymbolCapabilities>operator_doubleArrow(_documentSymbolCapabilities, _function_10);
            it_3.setDocumentSymbol(_doubleArrow_6);
            FormattingCapabilities _formattingCapabilities = new FormattingCapabilities();
            final Procedure1<FormattingCapabilities> _function_11 = (FormattingCapabilities it_4) -> {
              it_4.setDynamicRegistration(Boolean.valueOf(false));
            };
            FormattingCapabilities _doubleArrow_7 = ObjectExtensions.<FormattingCapabilities>operator_doubleArrow(_formattingCapabilities, _function_11);
            it_3.setFormatting(_doubleArrow_7);
            RangeFormattingCapabilities _rangeFormattingCapabilities = new RangeFormattingCapabilities();
            final Procedure1<RangeFormattingCapabilities> _function_12 = (RangeFormattingCapabilities it_4) -> {
              it_4.setDynamicRegistration(Boolean.valueOf(false));
            };
            RangeFormattingCapabilities _doubleArrow_8 = ObjectExtensions.<RangeFormattingCapabilities>operator_doubleArrow(_rangeFormattingCapabilities, _function_12);
            it_3.setRangeFormatting(_doubleArrow_8);
            OnTypeFormattingCapabilities _onTypeFormattingCapabilities = new OnTypeFormattingCapabilities();
            final Procedure1<OnTypeFormattingCapabilities> _function_13 = (OnTypeFormattingCapabilities it_4) -> {
              it_4.setDynamicRegistration(Boolean.valueOf(false));
            };
            OnTypeFormattingCapabilities _doubleArrow_9 = ObjectExtensions.<OnTypeFormattingCapabilities>operator_doubleArrow(_onTypeFormattingCapabilities, _function_13);
            it_3.setOnTypeFormatting(_doubleArrow_9);
            DefinitionCapabilities _definitionCapabilities = new DefinitionCapabilities();
            final Procedure1<DefinitionCapabilities> _function_14 = (DefinitionCapabilities it_4) -> {
              it_4.setDynamicRegistration(Boolean.valueOf(false));
            };
            DefinitionCapabilities _doubleArrow_10 = ObjectExtensions.<DefinitionCapabilities>operator_doubleArrow(_definitionCapabilities, _function_14);
            it_3.setDefinition(_doubleArrow_10);
            TypeDefinitionCapabilities _typeDefinitionCapabilities = new TypeDefinitionCapabilities();
            final Procedure1<TypeDefinitionCapabilities> _function_15 = (TypeDefinitionCapabilities it_4) -> {
              it_4.setDynamicRegistration(Boolean.valueOf(false));
            };
            TypeDefinitionCapabilities _doubleArrow_11 = ObjectExtensions.<TypeDefinitionCapabilities>operator_doubleArrow(_typeDefinitionCapabilities, _function_15);
            it_3.setTypeDefinition(_doubleArrow_11);
            ImplementationCapabilities _implementationCapabilities = new ImplementationCapabilities();
            final Procedure1<ImplementationCapabilities> _function_16 = (ImplementationCapabilities it_4) -> {
              it_4.setDynamicRegistration(Boolean.valueOf(false));
            };
            ImplementationCapabilities _doubleArrow_12 = ObjectExtensions.<ImplementationCapabilities>operator_doubleArrow(_implementationCapabilities, _function_16);
            it_3.setImplementation(_doubleArrow_12);
            CodeActionCapabilities _codeActionCapabilities = new CodeActionCapabilities();
            final Procedure1<CodeActionCapabilities> _function_17 = (CodeActionCapabilities it_4) -> {
              it_4.setDynamicRegistration(Boolean.valueOf(false));
            };
            CodeActionCapabilities _doubleArrow_13 = ObjectExtensions.<CodeActionCapabilities>operator_doubleArrow(_codeActionCapabilities, _function_17);
            it_3.setCodeAction(_doubleArrow_13);
            CodeLensCapabilities _codeLensCapabilities = new CodeLensCapabilities();
            final Procedure1<CodeLensCapabilities> _function_18 = (CodeLensCapabilities it_4) -> {
              it_4.setDynamicRegistration(Boolean.valueOf(false));
            };
            CodeLensCapabilities _doubleArrow_14 = ObjectExtensions.<CodeLensCapabilities>operator_doubleArrow(_codeLensCapabilities, _function_18);
            it_3.setCodeLens(_doubleArrow_14);
            DocumentLinkCapabilities _documentLinkCapabilities = new DocumentLinkCapabilities();
            final Procedure1<DocumentLinkCapabilities> _function_19 = (DocumentLinkCapabilities it_4) -> {
              it_4.setDynamicRegistration(Boolean.valueOf(false));
            };
            DocumentLinkCapabilities _doubleArrow_15 = ObjectExtensions.<DocumentLinkCapabilities>operator_doubleArrow(_documentLinkCapabilities, _function_19);
            it_3.setDocumentLink(_doubleArrow_15);
            ColorProviderCapabilities _colorProviderCapabilities = new ColorProviderCapabilities();
            final Procedure1<ColorProviderCapabilities> _function_20 = (ColorProviderCapabilities it_4) -> {
              it_4.setDynamicRegistration(Boolean.valueOf(false));
            };
            ColorProviderCapabilities _doubleArrow_16 = ObjectExtensions.<ColorProviderCapabilities>operator_doubleArrow(_colorProviderCapabilities, _function_20);
            it_3.setColorProvider(_doubleArrow_16);
            RenameCapabilities _renameCapabilities = new RenameCapabilities();
            final Procedure1<RenameCapabilities> _function_21 = (RenameCapabilities it_4) -> {
              it_4.setDynamicRegistration(Boolean.valueOf(false));
            };
            RenameCapabilities _doubleArrow_17 = ObjectExtensions.<RenameCapabilities>operator_doubleArrow(_renameCapabilities, _function_21);
            it_3.setRename(_doubleArrow_17);
          };
          TextDocumentClientCapabilities _doubleArrow = ObjectExtensions.<TextDocumentClientCapabilities>operator_doubleArrow(_textDocumentClientCapabilities, _function_3);
          it_2.setTextDocument(_doubleArrow);
          WorkspaceClientCapabilities _workspaceClientCapabilities = new WorkspaceClientCapabilities();
          it_2.setWorkspace(_workspaceClientCapabilities);
        };
        ClientCapabilities _doubleArrow = ObjectExtensions.<ClientCapabilities>operator_doubleArrow(_clientCapabilities, _function_2);
        it_1.setCapabilities(_doubleArrow);
      };
      InitializeParams _doubleArrow = ObjectExtensions.<InitializeParams>operator_doubleArrow(_initializeParams, _function_1);
      it.setParams(_doubleArrow);
    };
    final RequestMessage message = ObjectExtensions.<RequestMessage>operator_doubleArrow(_requestMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"1\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"method\": \"initialize\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"processId\": null,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"rootUri\": \"file:///tmp/foo\",");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"capabilities\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"workspace\": {},");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"textDocument\": {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"synchronization\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"willSave\": true,");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"willSaveWaitUntil\": false,");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"didSave\": true,");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"completion\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"completionItem\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"snippetSupport\": true,");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"commitCharactersSupport\": true,");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"documentationFormat\": [");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"plaintext\",");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"markdown\"");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"completionItemKind\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"valueSet\": [");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("2,");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("3");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"contextSupport\": false,");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"hover\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"contentFormat\": [");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"plaintext\",");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"markdown\"");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("],");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"signatureHelp\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"signatureInformation\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"documentationFormat\": [");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"plaintext\",");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"markdown\"");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"references\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"documentHighlight\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"documentSymbol\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"symbolKind\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"valueSet\": [");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("2,");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("3,");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("4,");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("5");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"formatting\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"rangeFormatting\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"onTypeFormatting\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"definition\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"typeDefinition\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"implementation\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"codeAction\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"codeLens\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"documentLink\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"colorProvider\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"rename\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"dynamicRegistration\": false");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testInitResponse() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = (ResponseMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId(12);
      InitializeResult _initializeResult = new InitializeResult();
      final Procedure1<InitializeResult> _function_1 = (InitializeResult it_1) -> {
        ServerCapabilities _serverCapabilities = new ServerCapabilities();
        it_1.setCapabilities(_serverCapabilities);
      };
      InitializeResult _doubleArrow = ObjectExtensions.<InitializeResult>operator_doubleArrow(_initializeResult, _function_1);
      it.setResult(_doubleArrow);
    };
    final ResponseMessage message = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": 12,");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"capabilities\": {}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testDidChange() {
    NotificationMessage _notificationMessage = new NotificationMessage();
    final Procedure1<NotificationMessage> _function = (NotificationMessage it) -> {
      it.setJsonrpc("2.0");
      it.setMethod(MessageMethods.DID_CHANGE_DOC);
      DidChangeTextDocumentParams _didChangeTextDocumentParams = new DidChangeTextDocumentParams();
      final Procedure1<DidChangeTextDocumentParams> _function_1 = (DidChangeTextDocumentParams it_1) -> {
        VersionedTextDocumentIdentifier _versionedTextDocumentIdentifier = new VersionedTextDocumentIdentifier();
        final Procedure1<VersionedTextDocumentIdentifier> _function_2 = (VersionedTextDocumentIdentifier it_2) -> {
          it_2.setUri("file:///tmp/foo");
        };
        VersionedTextDocumentIdentifier _doubleArrow = ObjectExtensions.<VersionedTextDocumentIdentifier>operator_doubleArrow(_versionedTextDocumentIdentifier, _function_2);
        it_1.setTextDocument(_doubleArrow);
        ArrayList<TextDocumentContentChangeEvent> _arrayList = new ArrayList<TextDocumentContentChangeEvent>();
        final Procedure1<ArrayList<TextDocumentContentChangeEvent>> _function_3 = (ArrayList<TextDocumentContentChangeEvent> it_2) -> {
          TextDocumentContentChangeEvent _textDocumentContentChangeEvent = new TextDocumentContentChangeEvent();
          final Procedure1<TextDocumentContentChangeEvent> _function_4 = (TextDocumentContentChangeEvent it_3) -> {
            Range _range = new Range();
            final Procedure1<Range> _function_5 = (Range it_4) -> {
              Position _position = new Position(7, 12);
              it_4.setStart(_position);
              Position _position_1 = new Position(8, 16);
              it_4.setEnd(_position_1);
            };
            Range _doubleArrow_1 = ObjectExtensions.<Range>operator_doubleArrow(_range, _function_5);
            it_3.setRange(_doubleArrow_1);
            it_3.setRangeLength(Integer.valueOf(20));
            it_3.setText("bar");
          };
          TextDocumentContentChangeEvent _doubleArrow_1 = ObjectExtensions.<TextDocumentContentChangeEvent>operator_doubleArrow(_textDocumentContentChangeEvent, _function_4);
          it_2.add(_doubleArrow_1);
        };
        ArrayList<TextDocumentContentChangeEvent> _doubleArrow_1 = ObjectExtensions.<ArrayList<TextDocumentContentChangeEvent>>operator_doubleArrow(_arrayList, _function_3);
        it_1.setContentChanges(_doubleArrow_1);
      };
      DidChangeTextDocumentParams _doubleArrow = ObjectExtensions.<DidChangeTextDocumentParams>operator_doubleArrow(_didChangeTextDocumentParams, _function_1);
      it.setParams(_doubleArrow);
    };
    final NotificationMessage message = ObjectExtensions.<NotificationMessage>operator_doubleArrow(_notificationMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"method\": \"textDocument/didChange\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"textDocument\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"version\": null,");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"uri\": \"file:///tmp/foo\"");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"contentChanges\": [");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"line\": 7,");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"character\": 12");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"line\": 8,");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"character\": 16");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"rangeLength\": 20,");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"text\": \"bar\"");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testPublishDiagnostics() {
    NotificationMessage _notificationMessage = new NotificationMessage();
    final Procedure1<NotificationMessage> _function = (NotificationMessage it) -> {
      it.setJsonrpc("2.0");
      it.setMethod(MessageMethods.SHOW_DIAGNOSTICS);
      PublishDiagnosticsParams _publishDiagnosticsParams = new PublishDiagnosticsParams();
      final Procedure1<PublishDiagnosticsParams> _function_1 = (PublishDiagnosticsParams it_1) -> {
        it_1.setUri("file:///tmp/foo");
        ArrayList<Diagnostic> _arrayList = new ArrayList<Diagnostic>();
        final Procedure1<ArrayList<Diagnostic>> _function_2 = (ArrayList<Diagnostic> it_2) -> {
          Diagnostic _diagnostic = new Diagnostic();
          final Procedure1<Diagnostic> _function_3 = (Diagnostic it_3) -> {
            Range _range = new Range();
            final Procedure1<Range> _function_4 = (Range it_4) -> {
              Position _position = new Position(4, 22);
              it_4.setStart(_position);
              Position _position_1 = new Position(4, 25);
              it_4.setEnd(_position_1);
            };
            Range _doubleArrow = ObjectExtensions.<Range>operator_doubleArrow(_range, _function_4);
            it_3.setRange(_doubleArrow);
            it_3.setSeverity(DiagnosticSeverity.Error);
            it_3.setMessage("Couldn\'t resolve reference to State \'bar\'.");
          };
          Diagnostic _doubleArrow = ObjectExtensions.<Diagnostic>operator_doubleArrow(_diagnostic, _function_3);
          it_2.add(_doubleArrow);
        };
        ArrayList<Diagnostic> _doubleArrow = ObjectExtensions.<ArrayList<Diagnostic>>operator_doubleArrow(_arrayList, _function_2);
        it_1.setDiagnostics(_doubleArrow);
      };
      PublishDiagnosticsParams _doubleArrow = ObjectExtensions.<PublishDiagnosticsParams>operator_doubleArrow(_publishDiagnosticsParams, _function_1);
      it.setParams(_doubleArrow);
    };
    final NotificationMessage message = ObjectExtensions.<NotificationMessage>operator_doubleArrow(_notificationMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"method\": \"textDocument/publishDiagnostics\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"uri\": \"file:///tmp/foo\",");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"diagnostics\": [");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"line\": 4,");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"character\": 22");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"line\": 4,");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"character\": 25");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"severity\": 1,");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"message\": \"Couldn\\u0027t resolve reference to State \\u0027bar\\u0027.\"");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testRenameResponse() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = (ResponseMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("12");
      WorkspaceEdit _workspaceEdit = new WorkspaceEdit();
      final Procedure1<WorkspaceEdit> _function_1 = (WorkspaceEdit it_1) -> {
        HashMap<String, List<TextEdit>> _hashMap = new HashMap<String, List<TextEdit>>();
        final Procedure1<HashMap<String, List<TextEdit>>> _function_2 = (HashMap<String, List<TextEdit>> it_2) -> {
          TextEdit _textEdit = new TextEdit();
          final Procedure1<TextEdit> _function_3 = (TextEdit it_3) -> {
            Range _range = new Range();
            final Procedure1<Range> _function_4 = (Range it_4) -> {
              Position _position = new Position(3, 32);
              it_4.setStart(_position);
              Position _position_1 = new Position(3, 35);
              it_4.setEnd(_position_1);
            };
            Range _doubleArrow = ObjectExtensions.<Range>operator_doubleArrow(_range, _function_4);
            it_3.setRange(_doubleArrow);
            it_3.setNewText("foobar");
          };
          TextEdit _doubleArrow = ObjectExtensions.<TextEdit>operator_doubleArrow(_textEdit, _function_3);
          TextEdit _textEdit_1 = new TextEdit();
          final Procedure1<TextEdit> _function_4 = (TextEdit it_3) -> {
            Range _range = new Range();
            final Procedure1<Range> _function_5 = (Range it_4) -> {
              Position _position = new Position(4, 22);
              it_4.setStart(_position);
              Position _position_1 = new Position(4, 25);
              it_4.setEnd(_position_1);
            };
            Range _doubleArrow_1 = ObjectExtensions.<Range>operator_doubleArrow(_range, _function_5);
            it_3.setRange(_doubleArrow_1);
            it_3.setNewText("foobar");
          };
          TextEdit _doubleArrow_1 = ObjectExtensions.<TextEdit>operator_doubleArrow(_textEdit_1, _function_4);
          it_2.put("file:///tmp/foo", CollectionLiterals.<TextEdit>newArrayList(_doubleArrow, _doubleArrow_1));
        };
        HashMap<String, List<TextEdit>> _doubleArrow = ObjectExtensions.<HashMap<String, List<TextEdit>>>operator_doubleArrow(_hashMap, _function_2);
        it_1.setChanges(_doubleArrow);
      };
      WorkspaceEdit _doubleArrow = ObjectExtensions.<WorkspaceEdit>operator_doubleArrow(_workspaceEdit, _function_1);
      it.setResult(_doubleArrow);
    };
    final ResponseMessage message = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"changes\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"file:///tmp/foo\": [");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"line\": 3,");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"character\": 32");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"line\": 3,");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"character\": 35");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"newText\": \"foobar\"");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"line\": 4,");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"character\": 22");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"line\": 4,");
    _builder.newLine();
    _builder.append("              ");
    _builder.append("\"character\": 25");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"newText\": \"foobar\"");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testHoverResponse1() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = (ResponseMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("12");
      Hover _hover = new Hover();
      final Procedure1<Hover> _function_1 = (Hover it_1) -> {
        Range _range = new Range();
        final Procedure1<Range> _function_2 = (Range it_2) -> {
          Position _position = new Position(3, 32);
          it_2.setStart(_position);
          Position _position_1 = new Position(3, 35);
          it_2.setEnd(_position_1);
        };
        Range _doubleArrow = ObjectExtensions.<Range>operator_doubleArrow(_range, _function_2);
        it_1.setRange(_doubleArrow);
        it_1.setContents(CollectionLiterals.<Either<String, MarkedString>>newArrayList(
          Either.<String, MarkedString>forLeft("foo"), 
          Either.<String, MarkedString>forLeft("boo shuby doo")));
      };
      Hover _doubleArrow = ObjectExtensions.<Hover>operator_doubleArrow(_hover, _function_1);
      it.setResult(_doubleArrow);
    };
    final ResponseMessage message = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"contents\": [");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"foo\",");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"boo shuby doo\"");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("],");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"line\": 3,");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"character\": 32");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"line\": 3,");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"character\": 35");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testHoverResponse2() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = (ResponseMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("12");
      Hover _hover = new Hover();
      final Procedure1<Hover> _function_1 = (Hover it_1) -> {
        MarkupContent _markupContent = new MarkupContent();
        final Procedure1<MarkupContent> _function_2 = (MarkupContent it_2) -> {
          it_2.setKind("plaintext");
          it_2.setValue("foo");
        };
        MarkupContent _doubleArrow = ObjectExtensions.<MarkupContent>operator_doubleArrow(_markupContent, _function_2);
        it_1.setContents(_doubleArrow);
      };
      Hover _doubleArrow = ObjectExtensions.<Hover>operator_doubleArrow(_hover, _function_1);
      it.setResult(_doubleArrow);
    };
    final ResponseMessage message = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"contents\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"kind\": \"plaintext\",");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"value\": \"foo\"");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testCodeLensResponse() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = (ResponseMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("12");
      CodeLens _codeLens = new CodeLens();
      final Procedure1<CodeLens> _function_1 = (CodeLens it_1) -> {
        Range _range = new Range();
        final Procedure1<Range> _function_2 = (Range it_2) -> {
          Position _position = new Position(3, 32);
          it_2.setStart(_position);
          Position _position_1 = new Position(3, 35);
          it_2.setEnd(_position_1);
        };
        Range _doubleArrow = ObjectExtensions.<Range>operator_doubleArrow(_range, _function_2);
        it_1.setRange(_doubleArrow);
        Command _command = new Command();
        final Procedure1<Command> _function_3 = (Command it_2) -> {
          it_2.setTitle("save");
          it_2.setCommand("saveCommand");
          JsonObject _jsonObject = new JsonObject();
          final Procedure1<JsonObject> _function_4 = (JsonObject it_3) -> {
            it_3.addProperty("uri", "file:/foo");
            it_3.addProperty("version", Integer.valueOf(5));
          };
          JsonObject _doubleArrow_1 = ObjectExtensions.<JsonObject>operator_doubleArrow(_jsonObject, _function_4);
          it_2.setArguments(CollectionLiterals.<Object>newArrayList(_doubleArrow_1));
        };
        Command _doubleArrow_1 = ObjectExtensions.<Command>operator_doubleArrow(_command, _function_3);
        it_1.setCommand(_doubleArrow_1);
        JsonObject _jsonObject = new JsonObject();
        final Procedure1<JsonObject> _function_4 = (JsonObject it_2) -> {
          it_2.addProperty("key", "value");
        };
        JsonObject _doubleArrow_2 = ObjectExtensions.<JsonObject>operator_doubleArrow(_jsonObject, _function_4);
        it_1.setData(CollectionLiterals.<Object>newArrayList(
          Integer.valueOf(42), 
          "qwert", _doubleArrow_2));
      };
      CodeLens _doubleArrow = ObjectExtensions.<CodeLens>operator_doubleArrow(_codeLens, _function_1);
      it.setResult(_doubleArrow);
    };
    final ResponseMessage message = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"range\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"start\": {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"line\": 3,");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"character\": 32");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"end\": {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"line\": 3,");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"character\": 35");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"command\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"title\": \"save\",");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"command\": \"saveCommand\",");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"arguments\": [");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"uri\": \"file:/foo\",");
    _builder.newLine();
    _builder.append("          ");
    _builder.append("\"version\": 5");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"data\": [");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("42,");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"qwert\",");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"key\": \"value\"");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testResponseError() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = (ResponseMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("12");
      ResponseError _responseError = new ResponseError();
      final Procedure1<ResponseError> _function_1 = (ResponseError it_1) -> {
        it_1.setCode(ResponseErrorCode.InvalidRequest);
        it_1.setMessage("Could not parse request.");
      };
      ResponseError _doubleArrow = ObjectExtensions.<ResponseError>operator_doubleArrow(_responseError, _function_1);
      it.setError(_doubleArrow);
    };
    final ResponseMessage message = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"error\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"code\": -32600,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"message\": \"Could not parse request.\"");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testCompletionResponse() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = (ResponseMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("12");
      CompletionList _completionList = new CompletionList();
      final Procedure1<CompletionList> _function_1 = (CompletionList it_1) -> {
        it_1.setIsIncomplete(true);
        CompletionItem _completionItem = new CompletionItem();
        final Procedure1<CompletionItem> _function_2 = (CompletionItem it_2) -> {
          it_2.setLabel("foo");
        };
        CompletionItem _doubleArrow = ObjectExtensions.<CompletionItem>operator_doubleArrow(_completionItem, _function_2);
        it_1.setItems(Collections.<CompletionItem>unmodifiableList(CollectionLiterals.<CompletionItem>newArrayList(_doubleArrow)));
      };
      CompletionList _doubleArrow = ObjectExtensions.<CompletionList>operator_doubleArrow(_completionList, _function_1);
      it.setResult(_doubleArrow);
    };
    final ResponseMessage message = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"result\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"isIncomplete\": true,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"items\": [");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("{");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("\"label\": \"foo\"");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("]");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testDocumentFormatting() {
    RequestMessage _requestMessage = new RequestMessage();
    final Procedure1<RequestMessage> _function = (RequestMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("12");
      it.setMethod(MessageMethods.DOC_FORMATTING);
      DocumentFormattingParams _documentFormattingParams = new DocumentFormattingParams();
      final Procedure1<DocumentFormattingParams> _function_1 = (DocumentFormattingParams it_1) -> {
        TextDocumentIdentifier _textDocumentIdentifier = new TextDocumentIdentifier("file:///tmp/foo");
        it_1.setTextDocument(_textDocumentIdentifier);
        FormattingOptions _formattingOptions = new FormattingOptions();
        final Procedure1<FormattingOptions> _function_2 = (FormattingOptions it_2) -> {
          it_2.setTabSize(4);
          it_2.setInsertSpaces(false);
        };
        FormattingOptions _doubleArrow = ObjectExtensions.<FormattingOptions>operator_doubleArrow(_formattingOptions, _function_2);
        it_1.setOptions(_doubleArrow);
        it_1.getOptions().putNumber("customProperty", Integer.valueOf((-7)));
      };
      DocumentFormattingParams _doubleArrow = ObjectExtensions.<DocumentFormattingParams>operator_doubleArrow(_documentFormattingParams, _function_1);
      it.setParams(_doubleArrow);
    };
    final RequestMessage message = ObjectExtensions.<RequestMessage>operator_doubleArrow(_requestMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"method\": \"textDocument/formatting\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"textDocument\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"uri\": \"file:///tmp/foo\"");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("},");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"options\": {");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"tabSize\": 4,");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"insertSpaces\": false,");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("\"customProperty\": -7");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testTelemetry() {
    NotificationMessage _notificationMessage = new NotificationMessage();
    final Procedure1<NotificationMessage> _function = (NotificationMessage it) -> {
      it.setJsonrpc("2.0");
      it.setMethod(MessageMethods.TELEMETRY_EVENT);
      JsonSerializeTest.TestObject _testObject = new JsonSerializeTest.TestObject();
      it.setParams(_testObject);
    };
    final NotificationMessage message = ObjectExtensions.<NotificationMessage>operator_doubleArrow(_notificationMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"method\": \"telemetry/event\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"params\": {");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"foo\": 12.3,");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("\"bar\": \"qwertz\"");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
  
  @Test
  public void testNullResponse() {
    ResponseMessage _responseMessage = new ResponseMessage();
    final Procedure1<ResponseMessage> _function = (ResponseMessage it) -> {
      it.setJsonrpc("2.0");
      it.setId("12");
    };
    final ResponseMessage message = ObjectExtensions.<ResponseMessage>operator_doubleArrow(_responseMessage, _function);
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("{");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"jsonrpc\": \"2.0\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"id\": \"12\",");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("\"result\": null");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.assertSerialize(message, _builder);
  }
}
