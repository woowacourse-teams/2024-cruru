import ReactQuill from 'react-quill-new';

import { ToolbarOptions } from '@customTypes/textEditor';
import 'react-quill-new/dist/quill.snow.css';
import './style.css';

interface TextEditorProps {
  width?: string;
  height?: string;
  toolbarOptions?: ToolbarOptions;
  value: string;
  onChange: (content: string) => void;
  onBlur?: () => void;
  placeholder?: string;
}

const defaultToolbarOptions: ToolbarOptions = [
  [{ header: [1, 2, 3, false] }],
  ['bold', 'italic', 'underline', 'blockquote', 'link'],
  [{ list: 'ordered' }, { list: 'bullet' }],
  [{ align: [] }, { color: [] }, { background: [] }],
  ['clean'],
];

export default function TextEditor({
  width = '100%',
  height = '100%',
  toolbarOptions = defaultToolbarOptions,
  value,
  onChange,
  onBlur,
  placeholder,
}: TextEditorProps) {
  return (
    <ReactQuill
      style={{ width, height }}
      modules={{ toolbar: toolbarOptions }}
      theme="snow"
      value={value}
      onChange={(content) => onChange(content)}
      onBlur={() => onBlur}
      placeholder={placeholder}
    />
  );
}
