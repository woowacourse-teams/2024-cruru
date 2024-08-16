import S from './style';
import Radio from '../Radio';

interface RadioOptionProps {
  label: string;
  checked: boolean;
  labelSize?: string;
  onChange: () => void;
}

export default function RadioOption({ label, checked, labelSize = '1.6rem', onChange }: RadioOptionProps) {
  return (
    <S.Option>
      <Radio
        isChecked={checked}
        onToggle={onChange}
      />
      <S.Label labelSize={labelSize}>{label}</S.Label>
    </S.Option>
  );
}
