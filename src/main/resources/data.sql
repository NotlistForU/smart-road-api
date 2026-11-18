
-- ============================================================
-- SMARTROAD - CATALOGO PADRONIZADO DE MANUTENCOES
-- PostgreSQL
-- Ordem: tipos -> categorias -> componentes -> relacionamentos -> aliases
-- ============================================================


-- ============================================================
-- 1) TIPOS DE MANUTENCAO
-- ============================================================

INSERT INTO maintenance_types (code, name, active) VALUES
('TROCA',           'Troca', true),
('REPARO',          'Reparo', true),
('LIMPEZA',         'Limpeza', true),
('HIGIENIZACAO',    'Higienização', true),
('INSPECAO',        'Inspeção', true),
('AJUSTE',          'Ajuste', true),
('LUBRIFICACAO',    'Lubrificação', true),
('INSTALACAO',      'Instalação', true),
('REVISAO',         'Revisão', true),
('ALINHAMENTO',     'Alinhamento', true),
('BALANCEAMENTO',   'Balanceamento', true),
('RODIZIO',         'Rodízio', true),
('CALIBRAGEM',      'Calibragem', true),
('RECARGA',         'Recarga', true),
('DIAGNOSTICO',     'Diagnóstico', true),
('PINTURA',         'Pintura', true),
('RETIFICA',        'Retífica', true)
    ON CONFLICT DO NOTHING;


-- ============================================================
-- 2) CATEGORIAS
-- ============================================================

INSERT INTO maintenance_categories (name, active) VALUES
('Motor', true),
('Freios', true),
('Suspensão e direção', true),
('Transmissão e embreagem', true),
('Pneus e rodas', true),
('Sistema elétrico', true),
('Arrefecimento e ar-condicionado', true),
('Carroceria e exterior', true),
('Interior e acabamento', true),
('Escapamento e combustível', true),
('Veículos elétricos e híbridos', true)
    ON CONFLICT DO NOTHING;


-- ============================================================
-- 3) COMPONENTES
-- ============================================================

INSERT INTO maintenance_items (category_id, name, active)

SELECT c.id, v.name, true

FROM (VALUES

-- MOTOR
('Motor', 'Óleo do motor'),
('Motor', 'Filtro de óleo'),
('Motor', 'Filtro de ar do motor'),
('Motor', 'Velas de ignição'),
('Motor', 'Bobinas de ignição'),
('Motor', 'Correia dentada'),
('Motor', 'Correia de acessórios'),
('Motor', 'Tensor da correia'),
('Motor', 'Bomba de óleo'),
('Motor', 'Junta do cabeçote'),
('Motor', 'Coxim do motor'),
('Motor', 'Retentores do motor'),
('Motor', 'Sensores do motor'),
('Motor', 'Cabeçote'),
('Motor', 'Bloco do motor'),
('Motor', 'Sistema de injeção'),
('Motor', 'Corpo de borboleta (TBI)'),
('Motor', 'Sistema de ignição'),
('Motor', 'Bicos injetores'),
('Motor', 'Compartimento do motor'),

-- FREIOS
('Freios', 'Pastilhas de freio'),
('Freios', 'Discos de freio'),
('Freios', 'Lonas de freio'),
('Freios', 'Tambores de freio'),
('Freios', 'Fluido de freio'),
('Freios', 'Pinças de freio'),
('Freios', 'Cilindro mestre'),
('Freios', 'Flexíveis de freio'),
('Freios', 'Freio de estacionamento'),
('Freios', 'Sistema hidráulico de freios'),
('Freios', 'Pedal de freio'),
('Freios', 'Sistema de freios'),

-- SUSPENSAO E DIRECAO
('Suspensão e direção', 'Amortecedores'),
('Suspensão e direção', 'Molas'),
('Suspensão e direção', 'Bandejas de suspensão'),
('Suspensão e direção', 'Buchas de suspensão'),
('Suspensão e direção', 'Pivôs de suspensão'),
('Suspensão e direção', 'Bieletas'),
('Suspensão e direção', 'Terminais de direção'),
('Suspensão e direção', 'Caixa de direção'),
('Suspensão e direção', 'Rolamentos de roda'),
('Suspensão e direção', 'Coifas de homocinética'),
('Suspensão e direção', 'Coxins de amortecedor'),
('Suspensão e direção', 'Suspensão dianteira'),
('Suspensão e direção', 'Suspensão traseira'),
('Suspensão e direção', 'Barra estabilizadora'),
('Suspensão e direção', 'Sistema de direção hidráulica'),
('Suspensão e direção', 'Sistema de direção elétrica'),
('Suspensão e direção', 'Geometria de direção'),

-- TRANSMISSAO E EMBREAGEM
('Transmissão e embreagem', 'Óleo do câmbio'),
('Transmissão e embreagem', 'Filtro do câmbio'),
('Transmissão e embreagem', 'Kit de embreagem'),
('Transmissão e embreagem', 'Disco de embreagem'),
('Transmissão e embreagem', 'Platô de embreagem'),
('Transmissão e embreagem', 'Rolamento de embreagem'),
('Transmissão e embreagem', 'Coxim do câmbio'),
('Transmissão e embreagem', 'Óleo do diferencial'),
('Transmissão e embreagem', 'Câmbio manual'),
('Transmissão e embreagem', 'Câmbio automático'),
('Transmissão e embreagem', 'Embreagem'),
('Transmissão e embreagem', 'Diferencial'),
('Transmissão e embreagem', 'Semi-eixo'),
('Transmissão e embreagem', 'Junta homocinética'),

-- PNEUS E RODAS
('Pneus e rodas', 'Pneus'),
('Pneus e rodas', 'Rodas'),
('Pneus e rodas', 'Válvulas dos pneus'),
('Pneus e rodas', 'Estepe'),

-- SISTEMA ELETRICO
('Sistema elétrico', 'Bateria 12V'),
('Sistema elétrico', 'Alternador'),
('Sistema elétrico', 'Motor de partida'),
('Sistema elétrico', 'Fusíveis'),
('Sistema elétrico', 'Relés'),
('Sistema elétrico', 'Lâmpadas dos faróis'),
('Sistema elétrico', 'Lâmpadas das lanternas'),
('Sistema elétrico', 'Sensores elétricos'),
('Sistema elétrico', 'Chicote elétrico'),
('Sistema elétrico', 'Motor do limpador de para-brisa'),
('Sistema elétrico', 'Palhetas do limpador de para-brisa'),
('Sistema elétrico', 'Vidros elétricos'),
('Sistema elétrico', 'Travas elétricas'),
('Sistema elétrico', 'Sistema elétrico'),

-- ARREFECIMENTO E AR-CONDICIONADO
('Arrefecimento e ar-condicionado', 'Líquido de arrefecimento'),
('Arrefecimento e ar-condicionado', 'Radiador'),
('Arrefecimento e ar-condicionado', 'Válvula termostática'),
('Arrefecimento e ar-condicionado', 'Mangueiras do radiador'),
('Arrefecimento e ar-condicionado', 'Reservatório de expansão'),
('Arrefecimento e ar-condicionado', 'Ventoinha'),
('Arrefecimento e ar-condicionado', 'Bomba d''água'),
('Arrefecimento e ar-condicionado', 'Compressor do ar-condicionado'),
('Arrefecimento e ar-condicionado', 'Filtro de cabine'),
('Arrefecimento e ar-condicionado', 'Condensador'),
('Arrefecimento e ar-condicionado', 'Evaporador'),
('Arrefecimento e ar-condicionado', 'Sistema de arrefecimento'),
('Arrefecimento e ar-condicionado', 'Dutos do ar-condicionado'),
('Arrefecimento e ar-condicionado', 'Gás refrigerante do ar-condicionado'),
('Arrefecimento e ar-condicionado', 'Sistema de ar-condicionado'),

-- CARROCERIA E EXTERIOR
('Carroceria e exterior', 'Para-choque'),
('Carroceria e exterior', 'Para-lama'),
('Carroceria e exterior', 'Capô'),
('Carroceria e exterior', 'Portas'),
('Carroceria e exterior', 'Retrovisores'),
('Carroceria e exterior', 'Faróis'),
('Carroceria e exterior', 'Lanternas'),
('Carroceria e exterior', 'Para-brisa'),
('Carroceria e exterior', 'Vidros laterais'),
('Carroceria e exterior', 'Vidro traseiro'),
('Carroceria e exterior', 'Maçanetas externas'),
('Carroceria e exterior', 'Grade frontal'),
('Carroceria e exterior', 'Fechaduras'),
('Carroceria e exterior', 'Pintura da carroceria'),

-- INTERIOR E ACABAMENTO
('Interior e acabamento', 'Bancos'),
('Interior e acabamento', 'Revestimento dos bancos'),
('Interior e acabamento', 'Tapetes'),
('Interior e acabamento', 'Cintos de segurança'),
('Interior e acabamento', 'Forro do teto'),
('Interior e acabamento', 'Painel'),
('Interior e acabamento', 'Volante'),
('Interior e acabamento', 'Manopla do câmbio'),
('Interior e acabamento', 'Maçanetas internas'),
('Interior e acabamento', 'Carpete'),
('Interior e acabamento', 'Interior do veículo'),

-- ESCAPAMENTO E COMBUSTIVEL
('Escapamento e combustível', 'Escapamento'),
('Escapamento e combustível', 'Silencioso'),
('Escapamento e combustível', 'Catalisador'),
('Escapamento e combustível', 'Sonda lambda'),
('Escapamento e combustível', 'Bomba de combustível'),
('Escapamento e combustível', 'Filtro de combustível'),
('Escapamento e combustível', 'Tanque de combustível'),
('Escapamento e combustível', 'Tubulação de combustível'),
('Escapamento e combustível', 'Sistema de alimentação'),

-- VEICULOS ELETRICOS E HIBRIDOS
('Veículos elétricos e híbridos', 'Bateria de tração'),
('Veículos elétricos e híbridos', 'Fluido de arrefecimento da bateria'),
('Veículos elétricos e híbridos', 'Filtro do sistema de refrigeração'),
('Veículos elétricos e híbridos', 'Óleo do redutor'),
('Veículos elétricos e híbridos', 'Cabos de alta tensão'),
('Veículos elétricos e híbridos', 'Inversor de potência'),
('Veículos elétricos e híbridos', 'Motor elétrico'),
('Veículos elétricos e híbridos', 'Carregador de bordo'),
('Veículos elétricos e híbridos', 'Porta de carregamento'),
('Veículos elétricos e híbridos', 'Sistema de gerenciamento da bateria')

     ) AS v(category, name)

         JOIN maintenance_categories c ON c.name = v.category

    ON CONFLICT DO NOTHING;


-- ============================================================
-- 4) TIPOS PERMITIDOS POR COMPONENTE
-- ============================================================

INSERT INTO maintenance_item_types (item_id, type_id)

SELECT i.id, t.id

FROM (VALUES

-- MOTOR
('Óleo do motor', 'TROCA'),
('Óleo do motor', 'INSPECAO'),
('Óleo do motor', 'LUBRIFICACAO'),
('Filtro de óleo', 'TROCA'),
('Filtro de óleo', 'INSPECAO'),
('Filtro de ar do motor', 'TROCA'),
('Filtro de ar do motor', 'INSPECAO'),
('Velas de ignição', 'TROCA'),
('Velas de ignição', 'INSPECAO'),
('Velas de ignição', 'DIAGNOSTICO'),
('Bobinas de ignição', 'TROCA'),
('Bobinas de ignição', 'REPARO'),
('Bobinas de ignição', 'DIAGNOSTICO'),
('Correia dentada', 'TROCA'),
('Correia dentada', 'INSPECAO'),
('Correia de acessórios', 'TROCA'),
('Correia de acessórios', 'AJUSTE'),
('Tensor da correia', 'TROCA'),
('Tensor da correia', 'AJUSTE'),
('Bomba de óleo', 'TROCA'),
('Bomba de óleo', 'REPARO'),
('Bomba de óleo', 'DIAGNOSTICO'),
('Junta do cabeçote', 'TROCA'),
('Junta do cabeçote', 'REPARO'),
('Coxim do motor', 'TROCA'),
('Coxim do motor', 'INSPECAO'),
('Retentores do motor', 'TROCA'),
('Retentores do motor', 'REPARO'),
('Sensores do motor', 'TROCA'),
('Sensores do motor', 'DIAGNOSTICO'),
('Cabeçote', 'REPARO'),
('Cabeçote', 'RETIFICA'),
('Cabeçote', 'INSPECAO'),
('Bloco do motor', 'REPARO'),
('Bloco do motor', 'RETIFICA'),
('Bloco do motor', 'DIAGNOSTICO'),
('Sistema de injeção', 'REPARO'),
('Sistema de injeção', 'LIMPEZA'),
('Sistema de injeção', 'DIAGNOSTICO'),
('Sistema de injeção', 'REVISAO'),
('Corpo de borboleta (TBI)', 'LIMPEZA'),
('Corpo de borboleta (TBI)', 'REPARO'),
('Corpo de borboleta (TBI)', 'AJUSTE'),
('Corpo de borboleta (TBI)', 'DIAGNOSTICO'),
('Sistema de ignição', 'REPARO'),
('Sistema de ignição', 'INSPECAO'),
('Sistema de ignição', 'DIAGNOSTICO'),
('Bicos injetores', 'LIMPEZA'),
('Bicos injetores', 'REPARO'),
('Bicos injetores', 'INSPECAO'),
('Compartimento do motor', 'LIMPEZA'),
('Compartimento do motor', 'HIGIENIZACAO'),
('Compartimento do motor', 'INSPECAO'),

-- FREIOS
('Pastilhas de freio', 'TROCA'),
('Pastilhas de freio', 'INSPECAO'),
('Discos de freio', 'TROCA'),
('Discos de freio', 'RETIFICA'),
('Discos de freio', 'INSPECAO'),
('Lonas de freio', 'TROCA'),
('Lonas de freio', 'INSPECAO'),
('Tambores de freio', 'TROCA'),
('Tambores de freio', 'REPARO'),
('Tambores de freio', 'RETIFICA'),
('Fluido de freio', 'TROCA'),
('Fluido de freio', 'INSPECAO'),
('Pinças de freio', 'TROCA'),
('Pinças de freio', 'REPARO'),
('Pinças de freio', 'LIMPEZA'),
('Cilindro mestre', 'TROCA'),
('Cilindro mestre', 'REPARO'),
('Flexíveis de freio', 'TROCA'),
('Flexíveis de freio', 'INSPECAO'),
('Freio de estacionamento', 'AJUSTE'),
('Freio de estacionamento', 'REPARO'),
('Freio de estacionamento', 'INSPECAO'),
('Sistema hidráulico de freios', 'REPARO'),
('Sistema hidráulico de freios', 'INSPECAO'),
('Sistema hidráulico de freios', 'DIAGNOSTICO'),
('Pedal de freio', 'AJUSTE'),
('Pedal de freio', 'REPARO'),
('Sistema de freios', 'INSPECAO'),
('Sistema de freios', 'REVISAO'),
('Sistema de freios', 'DIAGNOSTICO'),

-- SUSPENSAO E DIRECAO
('Amortecedores', 'TROCA'),
('Amortecedores', 'INSPECAO'),
('Molas', 'TROCA'),
('Molas', 'INSPECAO'),
('Bandejas de suspensão', 'TROCA'),
('Bandejas de suspensão', 'REPARO'),
('Buchas de suspensão', 'TROCA'),
('Pivôs de suspensão', 'TROCA'),
('Pivôs de suspensão', 'INSPECAO'),
('Bieletas', 'TROCA'),
('Terminais de direção', 'TROCA'),
('Terminais de direção', 'INSPECAO'),
('Caixa de direção', 'REPARO'),
('Caixa de direção', 'TROCA'),
('Caixa de direção', 'DIAGNOSTICO'),
('Rolamentos de roda', 'TROCA'),
('Rolamentos de roda', 'INSPECAO'),
('Coifas de homocinética', 'TROCA'),
('Coxins de amortecedor', 'TROCA'),
('Suspensão dianteira', 'REPARO'),
('Suspensão dianteira', 'INSPECAO'),
('Suspensão dianteira', 'REVISAO'),
('Suspensão traseira', 'REPARO'),
('Suspensão traseira', 'INSPECAO'),
('Suspensão traseira', 'REVISAO'),
('Barra estabilizadora', 'TROCA'),
('Barra estabilizadora', 'REPARO'),
('Sistema de direção hidráulica', 'REPARO'),
('Sistema de direção hidráulica', 'INSPECAO'),
('Sistema de direção elétrica', 'REPARO'),
('Sistema de direção elétrica', 'DIAGNOSTICO'),
('Geometria de direção', 'ALINHAMENTO'),
('Geometria de direção', 'INSPECAO'),

-- TRANSMISSAO E EMBREAGEM
('Óleo do câmbio', 'TROCA'),
('Óleo do câmbio', 'INSPECAO'),
('Óleo do câmbio', 'LUBRIFICACAO'),
('Filtro do câmbio', 'TROCA'),
('Filtro do câmbio', 'INSPECAO'),
('Kit de embreagem', 'TROCA'),
('Disco de embreagem', 'TROCA'),
('Platô de embreagem', 'TROCA'),
('Rolamento de embreagem', 'TROCA'),
('Coxim do câmbio', 'TROCA'),
('Coxim do câmbio', 'INSPECAO'),
('Óleo do diferencial', 'TROCA'),
('Óleo do diferencial', 'LUBRIFICACAO'),
('Óleo do diferencial', 'INSPECAO'),
('Câmbio manual', 'REPARO'),
('Câmbio manual', 'REVISAO'),
('Câmbio manual', 'DIAGNOSTICO'),
('Câmbio automático', 'REPARO'),
('Câmbio automático', 'REVISAO'),
('Câmbio automático', 'DIAGNOSTICO'),
('Embreagem', 'REPARO'),
('Embreagem', 'INSPECAO'),
('Diferencial', 'REPARO'),
('Diferencial', 'INSPECAO'),
('Semi-eixo', 'TROCA'),
('Semi-eixo', 'REPARO'),
('Junta homocinética', 'TROCA'),
('Junta homocinética', 'REPARO'),
('Junta homocinética', 'INSPECAO'),

-- PNEUS E RODAS
('Pneus', 'TROCA'),
('Pneus', 'REPARO'),
('Pneus', 'INSPECAO'),
('Pneus', 'RODIZIO'),
('Pneus', 'CALIBRAGEM'),
('Rodas', 'TROCA'),
('Rodas', 'REPARO'),
('Rodas', 'INSPECAO'),
('Rodas', 'BALANCEAMENTO'),
('Rodas', 'ALINHAMENTO'),
('Válvulas dos pneus', 'TROCA'),
('Válvulas dos pneus', 'REPARO'),
('Estepe', 'INSPECAO'),
('Estepe', 'TROCA'),
('Estepe', 'CALIBRAGEM'),

-- SISTEMA ELETRICO
('Bateria 12V', 'TROCA'),
('Bateria 12V', 'REPARO'),
('Bateria 12V', 'INSPECAO'),
('Bateria 12V', 'DIAGNOSTICO'),
('Alternador', 'TROCA'),
('Alternador', 'REPARO'),
('Alternador', 'DIAGNOSTICO'),
('Motor de partida', 'TROCA'),
('Motor de partida', 'REPARO'),
('Motor de partida', 'DIAGNOSTICO'),
('Fusíveis', 'TROCA'),
('Fusíveis', 'INSPECAO'),
('Relés', 'TROCA'),
('Relés', 'DIAGNOSTICO'),
('Lâmpadas dos faróis', 'TROCA'),
('Lâmpadas dos faróis', 'INSTALACAO'),
('Lâmpadas das lanternas', 'TROCA'),
('Lâmpadas das lanternas', 'INSTALACAO'),
('Sensores elétricos', 'TROCA'),
('Sensores elétricos', 'REPARO'),
('Sensores elétricos', 'DIAGNOSTICO'),
('Chicote elétrico', 'REPARO'),
('Chicote elétrico', 'INSPECAO'),
('Chicote elétrico', 'DIAGNOSTICO'),
('Motor do limpador de para-brisa', 'TROCA'),
('Motor do limpador de para-brisa', 'REPARO'),
('Palhetas do limpador de para-brisa', 'TROCA'),
('Palhetas do limpador de para-brisa', 'INSPECAO'),
('Vidros elétricos', 'REPARO'),
('Vidros elétricos', 'DIAGNOSTICO'),
('Travas elétricas', 'REPARO'),
('Travas elétricas', 'DIAGNOSTICO'),
('Sistema elétrico', 'REVISAO'),
('Sistema elétrico', 'INSPECAO'),
('Sistema elétrico', 'DIAGNOSTICO'),

-- ARREFECIMENTO E AR-CONDICIONADO
('Líquido de arrefecimento', 'TROCA'),
('Líquido de arrefecimento', 'INSPECAO'),
('Radiador', 'TROCA'),
('Radiador', 'REPARO'),
('Radiador', 'LIMPEZA'),
('Válvula termostática', 'TROCA'),
('Válvula termostática', 'INSPECAO'),
('Mangueiras do radiador', 'TROCA'),
('Mangueiras do radiador', 'INSPECAO'),
('Reservatório de expansão', 'TROCA'),
('Reservatório de expansão', 'LIMPEZA'),
('Ventoinha', 'TROCA'),
('Ventoinha', 'REPARO'),
('Ventoinha', 'DIAGNOSTICO'),
('Bomba d''água', 'TROCA'),
('Bomba d''água', 'REPARO'),
('Bomba d''água', 'INSPECAO'),
('Compressor do ar-condicionado', 'TROCA'),
('Compressor do ar-condicionado', 'REPARO'),
('Compressor do ar-condicionado', 'DIAGNOSTICO'),
('Filtro de cabine', 'TROCA'),
('Filtro de cabine', 'INSPECAO'),
('Condensador', 'TROCA'),
('Condensador', 'REPARO'),
('Condensador', 'LIMPEZA'),
('Evaporador', 'REPARO'),
('Evaporador', 'LIMPEZA'),
('Sistema de arrefecimento', 'REVISAO'),
('Sistema de arrefecimento', 'INSPECAO'),
('Sistema de arrefecimento', 'DIAGNOSTICO'),
('Dutos do ar-condicionado', 'LIMPEZA'),
('Dutos do ar-condicionado', 'HIGIENIZACAO'),
('Gás refrigerante do ar-condicionado', 'RECARGA'),
('Sistema de ar-condicionado', 'REVISAO'),
('Sistema de ar-condicionado', 'DIAGNOSTICO'),
('Sistema de ar-condicionado', 'LIMPEZA'),
('Sistema de ar-condicionado', 'HIGIENIZACAO'),

-- CARROCERIA E EXTERIOR
('Para-choque', 'TROCA'),
('Para-choque', 'REPARO'),
('Para-choque', 'PINTURA'),
('Para-lama', 'TROCA'),
('Para-lama', 'REPARO'),
('Para-lama', 'PINTURA'),
('Capô', 'TROCA'),
('Capô', 'REPARO'),
('Capô', 'PINTURA'),
('Portas', 'TROCA'),
('Portas', 'REPARO'),
('Portas', 'PINTURA'),
('Retrovisores', 'TROCA'),
('Retrovisores', 'REPARO'),
('Faróis', 'TROCA'),
('Faróis', 'REPARO'),
('Faróis', 'LIMPEZA'),
('Lanternas', 'TROCA'),
('Lanternas', 'REPARO'),
('Para-brisa', 'TROCA'),
('Para-brisa', 'REPARO'),
('Vidros laterais', 'TROCA'),
('Vidros laterais', 'REPARO'),
('Vidro traseiro', 'TROCA'),
('Vidro traseiro', 'REPARO'),
('Maçanetas externas', 'TROCA'),
('Maçanetas externas', 'REPARO'),
('Grade frontal', 'TROCA'),
('Grade frontal', 'REPARO'),
('Fechaduras', 'TROCA'),
('Fechaduras', 'REPARO'),
('Pintura da carroceria', 'PINTURA'),
('Pintura da carroceria', 'REPARO'),

-- INTERIOR E ACABAMENTO
('Bancos', 'REPARO'),
('Bancos', 'LIMPEZA'),
('Bancos', 'HIGIENIZACAO'),
('Revestimento dos bancos', 'TROCA'),
('Revestimento dos bancos', 'REPARO'),
('Revestimento dos bancos', 'LIMPEZA'),
('Tapetes', 'TROCA'),
('Tapetes', 'LIMPEZA'),
('Tapetes', 'HIGIENIZACAO'),
('Cintos de segurança', 'TROCA'),
('Cintos de segurança', 'REPARO'),
('Cintos de segurança', 'INSPECAO'),
('Forro do teto', 'TROCA'),
('Forro do teto', 'REPARO'),
('Forro do teto', 'HIGIENIZACAO'),
('Painel', 'REPARO'),
('Painel', 'LIMPEZA'),
('Painel', 'HIGIENIZACAO'),
('Volante', 'TROCA'),
('Volante', 'REPARO'),
('Volante', 'HIGIENIZACAO'),
('Manopla do câmbio', 'TROCA'),
('Manopla do câmbio', 'REPARO'),
('Maçanetas internas', 'TROCA'),
('Maçanetas internas', 'REPARO'),
('Carpete', 'TROCA'),
('Carpete', 'LIMPEZA'),
('Carpete', 'HIGIENIZACAO'),
('Interior do veículo', 'LIMPEZA'),
('Interior do veículo', 'HIGIENIZACAO'),

-- ESCAPAMENTO E COMBUSTIVEL
('Escapamento', 'TROCA'),
('Escapamento', 'REPARO'),
('Escapamento', 'INSPECAO'),
('Silencioso', 'TROCA'),
('Silencioso', 'REPARO'),
('Catalisador', 'TROCA'),
('Catalisador', 'REPARO'),
('Catalisador', 'DIAGNOSTICO'),
('Sonda lambda', 'TROCA'),
('Sonda lambda', 'DIAGNOSTICO'),
('Bomba de combustível', 'TROCA'),
('Bomba de combustível', 'REPARO'),
('Bomba de combustível', 'DIAGNOSTICO'),
('Filtro de combustível', 'TROCA'),
('Filtro de combustível', 'INSPECAO'),
('Tanque de combustível', 'REPARO'),
('Tanque de combustível', 'LIMPEZA'),
('Tubulação de combustível', 'TROCA'),
('Tubulação de combustível', 'REPARO'),
('Sistema de alimentação', 'LIMPEZA'),
('Sistema de alimentação', 'REVISAO'),
('Sistema de alimentação', 'DIAGNOSTICO'),

-- VEICULOS ELETRICOS E HIBRIDOS
('Bateria de tração', 'TROCA'),
('Bateria de tração', 'REPARO'),
('Bateria de tração', 'INSPECAO'),
('Bateria de tração', 'DIAGNOSTICO'),
('Fluido de arrefecimento da bateria', 'TROCA'),
('Fluido de arrefecimento da bateria', 'INSPECAO'),
('Filtro do sistema de refrigeração', 'TROCA'),
('Filtro do sistema de refrigeração', 'INSPECAO'),
('Óleo do redutor', 'TROCA'),
('Óleo do redutor', 'INSPECAO'),
('Óleo do redutor', 'LUBRIFICACAO'),
('Cabos de alta tensão', 'TROCA'),
('Cabos de alta tensão', 'REPARO'),
('Cabos de alta tensão', 'INSPECAO'),
('Cabos de alta tensão', 'DIAGNOSTICO'),
('Inversor de potência', 'REPARO'),
('Inversor de potência', 'DIAGNOSTICO'),
('Motor elétrico', 'REPARO'),
('Motor elétrico', 'DIAGNOSTICO'),
('Carregador de bordo', 'REPARO'),
('Carregador de bordo', 'DIAGNOSTICO'),
('Porta de carregamento', 'REPARO'),
('Porta de carregamento', 'INSPECAO'),
('Sistema de gerenciamento da bateria', 'DIAGNOSTICO'),
('Sistema de gerenciamento da bateria', 'INSPECAO'),
('Sistema de gerenciamento da bateria', 'REVISAO')

     ) AS v(item, type_code)

         JOIN maintenance_items i ON i.name = v.item
         JOIN maintenance_types t ON t.code = v.type_code

    ON CONFLICT DO NOTHING;


-- ============================================================
-- 5) ALIASES
-- ============================================================

INSERT INTO maintenance_item_aliases (item_id, alias, normalized_alias)

SELECT i.id, v.alias, v.normalized

FROM (VALUES

-- MOTOR
('Óleo do motor', 'Óleo motor', 'oleo motor'),
('Óleo do motor', 'Lubrificante do motor', 'lubrificante do motor'),
('Óleo do motor', 'Óleo do carro', 'oleo do carro'),

('Filtro de óleo', 'Filtro lubrificante', 'filtro lubrificante'),
('Filtro de óleo', 'Filtro de óleo do motor', 'filtro de oleo do motor'),

('Filtro de ar do motor', 'Filtro de ar', 'filtro de ar'),
('Filtro de ar do motor', 'Filtro de admissão', 'filtro de admissao'),

('Correia dentada', 'Correia de distribuição', 'correia de distribuicao'),

('Bomba d''água', 'Bomba de agua', 'bomba de agua'),

('Bicos injetores', 'Bico injetor', 'bico injetor'),
('Bicos injetores', 'Bicos de injeção', 'bicos de injecao'),

('Corpo de borboleta (TBI)', 'Corpo de borboleta', 'corpo de borboleta'),
('Corpo de borboleta (TBI)', 'TBI', 'tbi'),

-- FREIOS
('Pastilhas de freio', 'Pastilha de freio', 'pastilha de freio'),
('Pastilhas de freio', 'Pastilha freio', 'pastilha freio'),

('Discos de freio', 'Disco de freio', 'disco de freio'),
('Discos de freio', 'Disco freio', 'disco freio'),

-- SUSPENSAO
('Geometria de direção', 'Alinhamento', 'alinhamento'),
('Geometria de direção', 'Alinhamento de direção', 'alinhamento de direcao'),
('Geometria de direção', 'Cambagem', 'cambagem'),
('Amortecedores', 'Amortecedor', 'amortecedor'),
('Pivôs de suspensão', 'Pivo de suspensão', 'pivo de suspensao'),
('Bieletas', 'Bieleta', 'bieleta'),

-- TRANSMISSAO
('Óleo do câmbio', 'Óleo da transmissão', 'oleo da transmissao'),
('Óleo do câmbio', 'Fluido do câmbio', 'fluido do cambio'),

('Kit de embreagem', 'Embreagem completa', 'embreagem completa'),

('Junta homocinética', 'Homocinetica', 'homocinetica'),
('Junta homocinética', 'Junta CV', 'junta cv'),

('Óleo do diferencial', 'Óleo diferencial', 'oleo diferencial'),

-- PNEUS E RODAS
('Pneus', 'Pneu', 'pneu'),
('Pneus', 'Pneus do carro', 'pneus do carro'),

('Válvulas dos pneus', 'Válvula de pneu', 'valvula de pneu'),

-- SISTEMA ELETRICO
('Bateria 12V', 'Bateria auxiliar', 'bateria auxiliar'),
('Bateria 12V', 'Bateria automotiva', 'bateria automotiva'),

('Motor de partida', 'Motor de arranque', 'motor de arranque'),

('Faróis', 'Farol dianteiro', 'farol dianteiro'),
('Lanternas', 'Lanterna traseira', 'lanterna traseira'),

('Palhetas do limpador de para-brisa', 'Palheta do para-brisa', 'palheta do para brisa'),
('Palhetas do limpador de para-brisa', 'Limpador de para-brisa', 'limpador de para brisa'),
('Palhetas do limpador de para-brisa', 'Palheta limpadora', 'palheta limpadora'),

-- ARREFECIMENTO E AR-CONDICIONADO
('Líquido de arrefecimento', 'Aditivo do radiador', 'aditivo do radiador'),
('Líquido de arrefecimento', 'Fluido do radiador', 'fluido do radiador'),
('Líquido de arrefecimento', 'Água do radiador', 'agua do radiador'),

('Filtro de cabine', 'Filtro do ar condicionado', 'filtro do ar condicionado'),
('Filtro de cabine', 'Filtro antipólen', 'filtro antipolen'),
('Filtro de cabine', 'Filtro de pólen', 'filtro de polen'),

('Sistema de ar-condicionado', 'Ar condicionado', 'ar condicionado'),

('Gás refrigerante do ar-condicionado', 'Gás do ar condicionado', 'gas do ar condicionado'),

-- CARROCERIA
('Para-choque', 'Parachoque', 'parachoque'),
('Para-choque', 'Parachoques', 'parachoques'),

('Para-lama', 'Paralama', 'paralama'),

('Retrovisores', 'Espelho retrovisor', 'espelho retrovisor'),
('Retrovisores', 'Retrovisor externo', 'retrovisor externo'),

('Para-brisa', 'Parabrisa', 'parabrisa'),
('Para-brisa', 'Vidro dianteiro', 'vidro dianteiro'),
('Para-brisa', 'Vidro frontal', 'vidro frontal'),

('Maçanetas externas', 'Maçaneta da porta', 'macaneta da porta'),

('Grade frontal', 'Grade dianteira', 'grade dianteira'),

-- INTERIOR
('Revestimento dos bancos', 'Estofamento', 'estofamento'),
('Tapetes', 'Tapete automotivo', 'tapete automotivo'),
('Cintos de segurança', 'Cinto de segurança', 'cinto de seguranca'),
('Forro do teto', 'Teto interno', 'teto interno'),
('Carpete', 'Carpete automotivo', 'carpete automotivo'),

-- ESCAPAMENTO E COMBUSTIVEL
('Silencioso', 'Abafador', 'abafador'),
('Sonda lambda', 'Sensor de oxigênio', 'sensor de oxigenio'),
('Bomba de combustível', 'Bomba de gasolina', 'bomba de gasolina'),
('Filtro de combustível', 'Filtro de gasolina', 'filtro de gasolina'),
('Tanque de combustível', 'Tanque de gasolina', 'tanque de gasolina'),

-- ELETRICOS E HIBRIDOS
('Bateria de tração', 'Bateria de alta tensão', 'bateria de alta tensao'),
('Bateria de tração', 'Bateria principal', 'bateria principal'),

('Óleo do redutor', 'Lubrificante do redutor', 'lubrificante do redutor'),

('Cabos de alta tensão', 'Cabos HV', 'cabos hv'),

('Porta de carregamento', 'Entrada de carregamento', 'entrada de carregamento'),

('Filtro do sistema de refrigeração', 'Filtro de refrigeração da bateria', 'filtro de refrigeracao da bateria')

     ) AS v(item, alias, normalized)

         JOIN maintenance_items i ON i.name = v.item

    ON CONFLICT DO NOTHING;


-- ============================================================
-- FIM DO CATALOGO SMARTROAD
-- ============================================================
